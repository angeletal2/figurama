/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.figura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Conexion;
import modelo.entidades.figura.Material;

/**
 *
 * @author Angel
 */
public class MaterialDAO {

    private Connection conexion;

    public MaterialDAO() {
        this.conexion = new Conexion().getConexion();
    }
    
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    //Método para obtener los datos de un material en base a su id
    public Material getMaterialPorId(int id) {
        Material material = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM material WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                material = new Material(id, rs.getString("nombre"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
         }
        return material;
    }

    //Método que devuelve una lista con las materiales que no esten de baja
    public List<Material> getListaMateriales() {
        List<Material> materiales = new ArrayList();
        Material material;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM material");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                material = new Material(rs.getInt("id"), rs.getString("nombre"), rs.getInt("esBaja"));
                if(material.getEsBaja()==0){
                    materiales.add(material);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materiales;
    }
    
      //Método que devuelve una lista con todos los materiales existentes
    public List<Material> getListaMaterialesAdmin() {
        List<Material> materiales = new ArrayList();
        Material material;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM material");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                material = new Material(rs.getInt("id"), rs.getString("nombre"), rs.getInt("esBaja"));
                materiales.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materiales;
    }
    
    //Método que devuelve una lista de los materiales que componen la figura en base al id de la misma
      public List<Material> getListaMateriales(int idFigura) {
        List<Material> materiales = new ArrayList();
        Material material;
        try {
           PreparedStatement ps = conexion.prepareStatement("SELECT m.nombre " +
                     "FROM Figura f JOIN FiguraMaterial fm ON f.id = fm.idFigura " +
                     "JOIN Material m ON fm.idMaterial = m.id WHERE f.id = ?");
           
            ps.setInt(1, idFigura);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                material = new Material(rs.getString("nombre"), rs.getInt("esBaja"));
                materiales.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materiales;

    }
      
      //Método para obtener los datos de un personaje en base a su nombre
    public Material getMaterialPorNombre(String nombre) {
        Material material = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM material WHERE LOWER(nombre) = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                material = new Material(rs.getInt("id"),rs.getString("nombre"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }
    
     public void cambiarEstado(int idMaterial, int valorNuevo) {
        String sql = "UPDATE material SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idMaterial);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean materialExiste(String nombre) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM material where nombre = ?");
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

    public void anadirMaterial(Material material) {
        String sql = "INSERT INTO material (nombre, esBaja) VALUES (?,?)";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, material.getNombre());
            ps.setInt(2, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

