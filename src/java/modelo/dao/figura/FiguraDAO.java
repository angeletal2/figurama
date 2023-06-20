package modelo.dao.figura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Conexion;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
public class FiguraDAO {

    private Connection conexion;

    public FiguraDAO() {
        this.conexion = new Conexion().getConexion();

    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Figura getFiguraPorId(int id) {
        Figura figura = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                figura = new Figura(id, rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figura;
    }

    public Figura getFiguraPorNombre(String nombre) {
        Figura figura = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM Figura where LOWER(nombre) like ?");
            ps.setString(1, nombre.toLowerCase());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                figura = new Figura(rs.getInt("id"), nombre, rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figura;
    }

    // Método que devuelve una lista con todas las figuras existentes en la BD
    public List<Figura> getListaFiguras() {
        List<Figura> figuras = new ArrayList();
        Figura figura;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId())) {
                    figuras.add(figura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;
    }

    public List<Figura> getListaFigurasAdmin() {
        List<Figura> figuras = new ArrayList();
        Figura figura;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                figuras.add(figura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;
    }

    public boolean figuraExiste(String nombre) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura where nombre = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("nombre").equalsIgnoreCase(nombre)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método que devuelve una lista con todas las figuras existentes en la BD que concuerden con el filtro
    public List<Figura> getListaFiguras(String filtro, String tipo) {
        List<Figura> figuras = new ArrayList();

        Figura figura;
        try {
            PreparedStatement ps;
            if (tipo.equals("filtroTexto")) {
                ps = conexion.prepareStatement("SELECT * FROM Figura WHERE LOWER(nombre) LIKE ?");
                ps.setString(1, "%" + filtro.toLowerCase() + "%");
            } else {
                ps = conexion.prepareStatement("SELECT * FROM Figura f "
                        + "JOIN Personaje p ON p.id = f.idPersonaje "
                        + "WHERE LOWER(p.nombre) LIKE ? ");

                ps.setString(1, filtro.toLowerCase());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId())) {
                    figuras.add(figura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;

    }

    public double getPrecioMaximo() {
        Figura figura = new Figura();

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura WHERE precio-precio*descuento/100 = "
                    + "(SELECT MAX(precio-precio*descuento/100) "
                    + "FROM figura "
                    + "INNER JOIN Personaje ON figura.idPersonaje = Personaje.id "
                    + "INNER JOIN Serie ON Personaje.idSerie = Serie.id "
                    + "INNER JOIN Material ON figura.idMaterial = Material.id "
                    + "INNER JOIN Proveedor ON figura.idProveedor = Proveedor.id "
                    + "WHERE figura.esBaja = 0 "
                    + "AND Personaje.esBaja = 0 "
                    + "AND Serie.esBaja = 0 "
                    + "AND Material.esBaja = 0 "
                    + "AND Proveedor.esBaja = 0)");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return figura.getPrecioConDescuento();
    }

    public double getPrecioMinimo() {

        Figura figura = new Figura();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura WHERE precio-precio*descuento/100 = "
                    + "(SELECT MIN(precio-precio*descuento/100) "
                    + "FROM figura "
                    + "INNER JOIN Personaje ON figura.idPersonaje = Personaje.id "
                    + "INNER JOIN Serie ON Personaje.idSerie = Serie.id "
                    + "INNER JOIN Material ON figura.idMaterial = Material.id "
                    + "INNER JOIN Proveedor ON figura.idProveedor = Proveedor.id "
                    + "WHERE figura.esBaja = 0 "
                    + "AND Personaje.esBaja = 0 "
                    + "AND Serie.esBaja = 0 "
                    + "AND Material.esBaja = 0 "
                    + "AND Proveedor.esBaja = 0)");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return figura.getPrecioConDescuento();
    }

    public double getPrecioMaximo(String filtro) {
        Figura figura = new Figura();

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura WHERE precio-precio*descuento/100 = "
                    + "(SELECT MAX(precio-precio*descuento/100) "
                    + "FROM figura "
                    + "INNER JOIN Personaje ON figura.idPersonaje = Personaje.id "
                    + "INNER JOIN Serie ON Personaje.idSerie = Serie.id "
                    + "INNER JOIN Material ON figura.idMaterial = Material.id "
                    + "INNER JOIN Proveedor ON figura.idProveedor = Proveedor.id "
                    + "WHERE LOWER(figura.nombre) LIKE ? "
                    + "AND figura.esBaja = 0 "
                    + "AND Personaje.esBaja = 0 "
                    + "AND Serie.esBaja = 0 "
                    + "AND Material.esBaja = 0 "
                    + "AND Proveedor.esBaja = 0)");
            ps.setString(1, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figura.getPrecioConDescuento();
    }

    public double getPrecioMinimo(String filtro) {
        Figura figura = new Figura();

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM figura WHERE precio-precio*descuento/100 = "
                    + "(SELECT MIN(precio-precio*descuento/100) "
                    + "FROM figura "
                    + "INNER JOIN Personaje ON figura.idPersonaje = Personaje.id "
                    + "INNER JOIN Serie ON Personaje.idSerie = Serie.id "
                    + "INNER JOIN Material ON figura.idMaterial = Material.id "
                    + "INNER JOIN Proveedor ON figura.idProveedor = Proveedor.id "
                    + "WHERE LOWER(figura.nombre) LIKE ? "
                    + "AND figura.esBaja = 0 "
                    + "AND Personaje.esBaja = 0 "
                    + "AND Serie.esBaja = 0 "
                    + "AND Material.esBaja = 0 "
                    + "AND Proveedor.esBaja = 0)");
            ps.setString(1, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return figura.getPrecioConDescuento();
    }

    public List<Figura> getFigurasCatalogo(String[] series, String[] proveedores, String filtro, double precioMinimo, double precioMaximo, String orden) {
        List<Figura> figuras = new ArrayList();
        Figura figura;

        String[] seriesCheck = series[0].split(",");
        String[] proveedoresCheck = proveedores[0].split(",");

        try {
            String query;

            if (orden.equals("relevancias")) {
                query = "SELECT DISTINCT f.*, SUM(v.cantidad) AS totalVentas "
                        + "FROM Figura f "
                        + "JOIN Personaje p ON f.idPersonaje = p.id "
                        + "JOIN Serie s ON p.idSerie = s.id "
                        + "JOIN Proveedor pr ON f.idProveedor = pr.id "
                        + "LEFT JOIN Venta v ON f.id = v.idFigura ";
            } else {
                query = "SELECT DISTINCT f.* "
                        + "FROM Figura f "
                        + "JOIN Personaje p ON f.idPersonaje = p.id "
                        + "JOIN Serie s ON p.idSerie = s.id "
                        + "JOIN Proveedor pr ON f.idProveedor = pr.id ";
            }
            // Construir la cláusula WHERE dinámicamente
            String whereClause = "";
            List<String> parameters = new ArrayList();

            if (!series[0].equals("")) {
                whereClause += "WHERE LOWER(s.nombre) IN (";
                for (int i = 0; i < seriesCheck.length; i++) {
                    whereClause += "?";
                    parameters.add(seriesCheck[i].toLowerCase());
                    if (i != seriesCheck.length - 1) {
                        whereClause += ", ";
                    }
                }
                whereClause += ")";
            }
            if (!proveedores[0].equals("")) {

                if (!whereClause.isEmpty()) {
                    whereClause += "AND LOWER(pr.nombre) IN (";
                } else {
                    whereClause += "WHERE LOWER(pr.nombre) IN (";
                }

                for (int i = 0; i < proveedoresCheck.length; i++) {
                    whereClause += "?";
                    parameters.add(proveedoresCheck[i].toLowerCase());
                    if (i != proveedoresCheck.length - 1) {
                        whereClause += ", ";
                    }
                }
                whereClause += ")";
            }

            // Agregar la cláusula para filtrar por precio mínimo y máximo
            if (!whereClause.isEmpty()) {
                whereClause += "AND ";
            } else {
                whereClause += "WHERE ";
            }
            whereClause += "f.precio-f.precio*descuento/100 >= ? AND f.precio-f.precio*descuento/100 <= ?";
            parameters.add(Double.toString(precioMinimo));
            parameters.add(Double.toString(precioMaximo));

            if (!filtro.equals("")) {
                whereClause += " AND LOWER(f.nombre) like ?";
                parameters.add("%" + filtro.trim() + "%");
            }
            String valorOrder = "";

            switch (orden.trim()) {
                case "relevancias":
                    valorOrder = " GROUP BY f.id ORDER BY totalVentas DESC, f.nombre ASC";
                    break;
                case "recientes":
                    valorOrder = " ORDER BY f.fechaSalida DESC, f.nombre ASC";
                    break;
                case "antiguo":
                    valorOrder = " ORDER BY f.fechaSalida ASC, f.nombre ASC";
                    break;
                case "ofertas":
                    valorOrder = " ORDER BY f.descuento DESC, f.nombre ASC";

                    break;
                case "az":
                    valorOrder = " ORDER BY f.nombre ASC";

                    break;
                case "za":
                    valorOrder = " ORDER BY f.nombre DESC";

                    break;
                case "preciomenosamas":
                    valorOrder = "ORDER BY f.precio-f.precio*descuento/100 ASC, f.nombre ASC";
                    break;
                case "preciomasamenos":
                    valorOrder = "ORDER BY f.precio-f.precio*descuento/100 DESC, f.nombre ASC";
                    break;

            }

            whereClause += valorOrder;

            query += whereClause;
            PreparedStatement ps = conexion.prepareStatement(query);
            // Configurar los parámetros de la cláusula WHERE
            for (int i = 0; i < parameters.size(); i++) {
                ps.setString(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId())) {
                    figuras.add(figura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return figuras;
    }

     public List<Figura> getListaFigurasMasVendidas() {
        List<Figura> figuras = new ArrayList();
        Figura figura;
        try {
                        int contador = 0;

            PreparedStatement ps = conexion.prepareStatement("SELECT f.*, SUM(v.cantidad) AS totalVentas "
                    + "FROM Figura f "
                    + "LEFT JOIN Venta v ON f.id = v.idFigura "
                    + "GROUP BY f.id ORDER BY totalVentas DESC, f.nombre ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId()) && contador < 5) {
                    figuras.add(figura);
                    contador++;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;
    }

    public List<Figura> getListaFigurasMasRecientes() {
        List<Figura> figuras = new ArrayList();
        Figura figura;
        try {
            int contador = 0;
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM Figura  where esBaja=0 ORDER BY fechaSalida DESC, nombre ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId()) && contador < 5) {
                    figuras.add(figura);
                    contador++;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;
    }

    public List<Figura> getListaFigurasEnOferta() {
        List<Figura> figuras = new ArrayList();
        Figura figura;
        try {
            int contador = 0;
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM Figura WHERE descuento NOT LIKE 0 ORDER BY descuento DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = new Figura(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaSalida"), rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("altura"), rs.getInt("idPersonaje"), rs.getInt("idProveedor"), rs.getInt("descuento"), rs.getInt("idMaterial"), rs.getInt("esBaja"));
                if (figuraDisponible(figura.getId()) && contador < 5) {
                    figuras.add(figura);
                    contador++;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return figuras;
    }

    public boolean modificarFigura(Figura figura) {
        boolean resultado = false;
        String sql = "UPDATE figura SET nombre = ?, descripcion = ?, fechaSalida = ?, precio = ?, stock = ?, altura = ?, descuento = ?, idPersonaje = ?, idProveedor = ?, idMaterial = ? WHERE id = ?";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, figura.getNombre());
            ps.setString(2, figura.getDescripcion());
            ps.setDate(3, new java.sql.Date(figura.getFechaSalida().getTime()));
            ps.setDouble(4, figura.getPrecio());
            ps.setInt(5, figura.getStock());
            ps.setInt(6, figura.getAltura());
            ps.setInt(7, figura.getPorcentajeDescuento());
            ps.setInt(8, figura.getIdPersonaje());
            ps.setInt(9, figura.getIdProveedor());
            ps.setInt(10, figura.getIdMaterial());
            ps.setInt(11, figura.getId());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void anadirFigura(Figura figura) {
        String sql = "INSERT INTO figura (nombre,descripcion, fechaSalida,precio,stock,altura,descuento,idPersonaje,idProveedor,idMaterial, esBaja) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO imagen (url,idFigura) VALUES (?,?)";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, figura.getNombre());
            ps.setString(2, figura.getDescripcion());
            ps.setDate(3, new java.sql.Date(figura.getFechaSalida().getTime()));
            ps.setDouble(4, figura.getPrecio());
            ps.setInt(5, figura.getStock());
            ps.setInt(6, figura.getAltura());
            ps.setInt(7, figura.getPorcentajeDescuento());
            ps.setInt(8, figura.getIdPersonaje());
            ps.setInt(9, figura.getIdProveedor());
            ps.setInt(10, figura.getIdMaterial());
            ps.setInt(11, 0);

            ps.executeUpdate();

            int idFigura = 0;
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idFigura = rs.getInt(1);
            }
            for (int i = 1; i <= 3; i++) {
                PreparedStatement ps2 = conexion.prepareStatement(sql2);
                ps2.setString(1, figura.getNombre().replace(" ", "_") + "_" + i + ".jpg");
                ps2.setInt(2, idFigura);
                ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cambiarEstado(int idFigura, int valorNuevo) {
        String sql = "UPDATE figura SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idFigura);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumeroVentas(int id) {
        int numeroVentas = 0;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT SUM(cantidad) as numVentas FROM Venta where idFigura = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                numeroVentas = rs.getInt("numVentas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeroVentas;
    }

    //Método que comprueba si una figura está o no disponible buscando si el ella misma, su personaje, su serie, su material o su proveedor estan de baja
    public boolean figuraDisponible(int id) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT f.id "
                    + "FROM Figura f "
                    + "JOIN Personaje p ON f.idPersonaje = p.id "
                    + "JOIN Serie s ON p.idSerie = s.id "
                    + "JOIN Material m ON f.idMaterial = m.id "
                    + "JOIN Proveedor pr ON f.idProveedor = pr.id "
                    + "WHERE f.esBaja = 0 "
                    + "  AND p.esBaja = 0 "
                    + "  AND s.esBaja = 0 "
                    + "  AND m.esBaja = 0 "
                    + "  AND pr.esBaja = 0 "
                    + "  AND f.id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
