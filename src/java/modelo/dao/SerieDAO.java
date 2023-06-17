package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Conexion;
import modelo.entidades.Serie;

/**
 *
 * @author Angel
 */
public class SerieDAO {

    private Connection conexion;

    public SerieDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Serie getSeriePorId(int id) {
        Serie serie = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM serie WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                serie = new Serie(id, rs.getString("imagenURL"), rs.getString("nombre"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serie;
    }

    public Serie getSeriePorNombre(String nombre) {
        Serie serie = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM serie WHERE LOWER(nombre) = ?");
            ps.setString(1, nombre.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                serie = new Serie(rs.getInt("id"), rs.getString("imagenURL"), rs.getString("nombre"), rs.getInt("esBaja"));
                if(serie.getEsBaja()==1){
                    serie=null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serie;
    }

    public List<Serie> getListaSeries() {
        List<Serie> series = new ArrayList();
        Serie serie;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM serie");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                serie = new Serie(rs.getInt("id"), rs.getString("imagenUrl"), rs.getString("nombre"), rs.getInt("esBaja"));
                if (serie.getEsBaja() == 0) {
                    series.add(serie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;

    }
    
    
     public List<Serie> getListaSeriesAdmin() {
        List<Serie> series = new ArrayList();
        Serie serie;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM serie");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                serie = new Serie(rs.getInt("id"), rs.getString("imagenUrl"), rs.getString("nombre"), rs.getInt("esBaja"));
                    series.add(serie);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;

    }

    public void cambiarEstado(int idSerie, int valorNuevo) {
        String sql = "UPDATE serie SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idSerie);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean modificarSerie(Serie serie) {
        boolean resultado = false;
        String sql = "UPDATE seroe SET nombre = ?, imagenUrl = ? WHERE id = ?";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, serie.getNombre());
            ps.setString(2, serie.getUrl());

            ps.setInt(3, serie.getId());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean serieExiste(String nombre) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM serie where nombre = ?");
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

    public void anadirSerie(Serie serie) {
        String sql = "INSERT INTO serie (nombre,imagenUrl,esBaja) VALUES (?,?,?)";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, serie.getNombre());
            ps.setString(2, serie.getUrl());
            ps.setInt(3, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
