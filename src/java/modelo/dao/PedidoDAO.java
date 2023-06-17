package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.figura.FiguraDAO;
import modelo.entidades.ArticuloPedido;
import modelo.entidades.Conexion;
import modelo.entidades.Pedido;

/**
 *
 * @author Angel
 */
public class PedidoDAO {

    private Connection conexion;

    public PedidoDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public List<Pedido> obtenerPedidos() {
        List<Pedido> pedidos = new ArrayList();

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM pedido");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pedidos.add(new Pedido(rs.getInt("id"), rs.getDate("fecha"), rs.getString("estado"), rs.getInt("idUsuario"), rs.getString("direccion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> obtenerPedidos(int idUsuario) {
        List<Pedido> pedidos = new ArrayList();

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM pedido WHERE idUsuario = ?");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pedidos.add(new Pedido(rs.getInt("id"), rs.getDate("fecha"), rs.getString("estado"), rs.getInt("idUsuario"), rs.getString("direccion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public List<ArticuloPedido> obtenerDatosPedido(int idPedido) {
        List<ArticuloPedido> articulos = new ArrayList();

        try {
            FiguraDAO fdao = new FiguraDAO();
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM venta WHERE idPedido = ?");
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articulos.add(new ArticuloPedido(rs.getInt("id"), rs.getInt("cantidad"), rs.getDouble("precioUd"), rs.getInt("idPedido"), fdao.getFiguraPorId(rs.getInt("idFigura"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    public void modificarEstado(String estado, int idPedido) {

        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE pedido SET estado = ? WHERE id = ?");
            ps.setString(1, estado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
