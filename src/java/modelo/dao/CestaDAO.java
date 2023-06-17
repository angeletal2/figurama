package modelo.dao;

import modelo.dao.figura.FiguraDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.ArticuloCesta;
import modelo.entidades.Cesta;
import modelo.entidades.Conexion;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
public class CestaDAO {

    private Connection conexion;

    public CestaDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public List<ArticuloCesta> obtenerCesta(int idUsu) {
        List<ArticuloCesta> cesta = new ArrayList();

        Figura figura;
        FiguraDAO fdao = new FiguraDAO();
        int cantidad;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM cesta WHERE idUsuario = ?");
            ps.setInt(1, idUsu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = fdao.getFiguraPorId(rs.getInt("idFigura"));
                cantidad = rs.getInt("cantidad");
                cesta.add(new ArticuloCesta(figura, cantidad));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cesta;
    }
    // Método para añadir una figura a la cesta de un usuario

    public boolean anadirArticuloCesta(ArticuloCesta articulo, int idUsuario) {
        boolean resultado = false;
        String sql = "INSERT INTO cesta (idFigura, idUsuario, cantidad) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, articulo.getFigura().getId());
            ps.setInt(2, idUsuario);
            ps.setInt(3, articulo.getCantidad());

            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Método para modificar el stock de una figura existente en la cesta de un usuario
    public void actualizarCantidad(ArticuloCesta articulo, int idUsuario) {

        String sql = "UPDATE cesta SET cantidad = ? WHERE idFigura = ? AND idUsuario = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, articulo.getCantidad());
            ps.setInt(2, articulo.getFigura().getId());
            ps.setInt(3, idUsuario);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Método para eliminar una figura de la lista de deseos de un usuario
    public boolean eliminarArticuloCesta(ArticuloCesta articulo, int idUsuario) {
        boolean resultado = false;

        String sql = "DELETE FROM cesta WHERE idFigura = ? AND idUsuario = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, articulo.getFigura().getId());
            ps.setInt(2, idUsuario);

            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Método para guardar la cesta entera del usuario, este se usa cuando un usuario
    // sin logarse añade articulos a la cesta y despues inicia sesión
    public void guardarCesta(List<ArticuloCesta> cesta, int idUsuario) {
        List<ArticuloCesta> yaEnCesta = obtenerCesta(idUsuario);

        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO cesta (idFigura, idUsuario, cantidad) VALUES (?, ?, ?)");
            PreparedStatement ps2 = conexion.prepareStatement("UPDATE cesta SET cantidad = LEAST(cantidad + ?, (SELECT stock FROM figura WHERE id = ?)) WHERE idFigura = ? AND idUsuario = ?");

            for (ArticuloCesta articulo : cesta) {
                if (!yaEnCesta.contains(articulo)) {
                    ps.setInt(1, articulo.getFigura().getId());
                    ps.setInt(2, idUsuario);
                    ps.setInt(3, articulo.getCantidad());
                    ps.executeUpdate();
                } else {
                    ps2.setInt(1, articulo.getCantidad());
                    ps2.setInt(2, articulo.getFigura().getId());
                    ps2.setInt(3, articulo.getFigura().getId());
                    ps2.setInt(4, idUsuario);
                    ps2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar si el stock que se quiere añadir esta disponible o no
    public boolean stockDisponible(ArticuloCesta articulo) {
        boolean disponible = false;
        int cantidad = articulo.getCantidad();

        String sql = "SELECT stock FROM figura WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, articulo.getFigura().getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                disponible = stock >= cantidad;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disponible;
    }

  public void vaciarCesta(int idUsuario) {
    String sql = "DELETE FROM cesta WHERE idUsuario = ? AND idFigura IN " +
                 "(SELECT f.id FROM Figura f " +
                 "INNER JOIN Personaje p ON f.idPersonaje = p.id " +
                 "INNER JOIN Serie s ON p.idSerie = s.id " +
                 "INNER JOIN Material m ON f.idMaterial = m.id " +
                 "INNER JOIN Proveedor pr ON f.idProveedor = pr.id " +
                 "WHERE f.esBaja = 0 AND p.esBaja = 0 AND s.esBaja = 0 AND m.esBaja = 0 AND pr.esBaja = 0)";
    try {
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idUsuario);

        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    public List<Figura> comprar(Cesta cesta, int idUsuario, String direccion) {
        List<Figura> figuras = new ArrayList();
        boolean ventaExitosa;

        try {
            //Intento de venta con trasaccion
            ventaExitosa = venderProductos(idUsuario, cesta, direccion);

            //Si da error, actualizar la cesta
            if (!ventaExitosa) {
                FiguraDAO fdao = new FiguraDAO();
                //  Generar una cesta nueva con el stock actualizado
                for (ArticuloCesta articulo : cesta.getArticulos()) {
                    int cantidadCesta = articulo.getCantidad();
                    int stock = fdao.getFiguraPorId(articulo.getFigura().getId()).getStock();
                    if (cantidadCesta > stock) {
                        // No hay suficiente stock para realizar la venta
                        Figura figura = new Figura();
                        figura.setId(articulo.getFigura().getId());
                        figura.setStock(stock);
                        figura.setNombre(articulo.getFigura().getNombre());
                        figuras.add(figura);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Si ha fallado algo se devuelve la cesta actualizada, sino se devuelve
        //una cesta vacía que representa que todo estuvo bien
        return figuras;
    }

    private boolean venderProductos(int idUsuario, Cesta cesta, String direccion) throws SQLException {
        List<ArticuloCesta> articulos = cesta.getArticulos();
        FiguraDAO fdao = new FiguraDAO();

        try {
            conexion.setAutoCommit(false); // Desactivar el autocommit para realizar una transacción

            String sql = "INSERT INTO pedido (fecha, estado, idUsuario, direccion) VALUES (now(), ?, ?, ?)";

            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, "Pendiente de envío");
            ps.setInt(2, idUsuario);
            ps.setString(3, direccion);

            // Ejecutar la inserción
            ps.executeUpdate();

            int idPedido = 0;
            // Obtener las claves generadas (IDs)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }

            PreparedStatement psFigura;
            PreparedStatement psVenta;
            // Verificar si hay suficiente stock para cada artículo
            for (ArticuloCesta articulo : articulos) {
                int cantidadCesta = articulo.getCantidad();
                int stock = fdao.getFiguraPorId(articulo.getFigura().getId()).getStock();
                int nuevoStock = stock - cantidadCesta;

                //Si el stock no deseado en la compra de algún producto no es válido, que lance una excepcion
                if (nuevoStock < 0) {
                    throw new SQLException();
                }

                // Actualizar el stock de la figura en la base de datos
                String updateStock = "UPDATE Figura SET stock = ? WHERE id = ?";
                psFigura = conexion.prepareStatement(updateStock);
                psFigura.setInt(1, nuevoStock);
                psFigura.setInt(2, articulo.getFigura().getId());
                psFigura.executeUpdate();

                // Insertar la venta en la base de datos
                String insertVenta = "INSERT INTO Venta (cantidad, precioUd, idPedido, idFigura) VALUES (? , ?, ?, ?)";
                psVenta = conexion.prepareStatement(insertVenta);
                psVenta.setInt(1, articulo.getCantidad());
                psVenta.setDouble(2, articulo.getFigura().getPrecioConIva());
                psVenta.setInt(3, idPedido);
                psVenta.setInt(4, articulo.getFigura().getId());
                psVenta.executeUpdate();
            }

            conexion.commit(); // Confirmar la transacción
            return true;
        } catch (SQLException e) {
            conexion.rollback();
        }
        return false;

    }

}
