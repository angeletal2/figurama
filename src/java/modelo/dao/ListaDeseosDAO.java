/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import modelo.dao.figura.FiguraDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Conexion;
import modelo.entidades.figura.Figura;
import modelo.entidades.ArticuloListaDeseos;

/**
 *
 * @author Angel
 */
public class ListaDeseosDAO {
    private Connection conexion;

    public ListaDeseosDAO() {
        this.conexion = new Conexion().getConexion();
    }
    
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public List<ArticuloListaDeseos> obtenerListaDeseos(int idUsu) {
        List<ArticuloListaDeseos> listaDeseos = new ArrayList();

        Figura figura;
        FiguraDAO fdao = new FiguraDAO();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM listadeseos WHERE idUsuario = ?");
            ps.setInt(1, idUsu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                figura = fdao.getFiguraPorId(rs.getInt("idFigura"));
                listaDeseos.add(new ArticuloListaDeseos(figura));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeseos;
    }
 
    // Método para añadir una figura a la lista de deseos de un usuario
    public boolean anadirListaDeseos(int idFigura, int idUsuario) {
        boolean resultado = false;
        String sql = "INSERT INTO listadeseos (idFigura, idUsuario) VALUES (?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idFigura);
            ps.setInt(2, idUsuario);
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

// Método para eliminar una figura de la lista de deseos de un usuario
    public boolean eliminarListaDeseos(int idFigura, int idUsuario) {
        boolean resultado = false;

        String sql = "DELETE FROM listadeseos WHERE idFigura = ? AND idUsuario = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idFigura);
            ps.setInt(2, idUsuario);
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

}
