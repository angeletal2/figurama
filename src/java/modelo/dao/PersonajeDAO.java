/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Conexion;
import modelo.entidades.Personaje;

/**
 *
 * @author Angel
 */
public class PersonajeDAO {

    private Connection conexion;

    public PersonajeDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    //Método para obtener los datos de un personaje en base a su id
    public Personaje getPersonajePorId(int id) {
        Personaje personaje = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personaje WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                personaje = new Personaje(rs.getString("nombre"), rs.getInt("idSerie"), rs.getString("imagenURL"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personaje;
    }

    //Método para obtener los datos de un personaje en base a su nombre
    public Personaje getPersonajePorNombre(String nombre) {
        Personaje personaje = null;
        try {
            SerieDAO sdao = new SerieDAO();
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personaje WHERE LOWER(nombre) = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                personaje = new Personaje(rs.getInt("id"), rs.getString("nombre"), rs.getInt("idSerie"), rs.getString("imagenURL"), rs.getInt("esBaja"));
                if (personaje.getEsBaja() == 1 || sdao.getSeriePorId(personaje.getIdSerie()).getEsBaja() == 1) {
                    personaje = null;
                }
            }
            sdao.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personaje;
    }

    //Método para obtener los personajes de una serie en base a su nombre
    public List<Personaje> getPersonajesPorSerie(String nombreSerie) {
        List<Personaje> personajes = new ArrayList();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT p.* FROM personaje p JOIN serie s ON p.idSerie = s.id WHERE s.nombre = ?");
            ps.setString(1, nombreSerie.toLowerCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personaje personaje = new Personaje(rs.getString("nombre"), rs.getInt("idSerie"), rs.getString("imagenURL"), rs.getInt("esBaja"));

                if (personaje.getEsBaja() == 0) {
                    personajes.add(personaje);

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personajes;
    }

    //Método para obtener la seria a la que pertenece un personaje
    public String getSerie(String nombrePersonaje) {
        String nombre = "";
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT s.nombre FROM serie s JOIN personaje p ON p.idSerie = s.id WHERE p.nombre = ?");
            ps.setString(1, nombrePersonaje.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    public List<Personaje> getListaPersonajes() {
        List<Personaje> personajes = new ArrayList();
        Personaje personaje;
        try {
            SerieDAO sdao = new SerieDAO();
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personaje");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                personaje = new Personaje(rs.getInt("id"), rs.getString("nombre"), rs.getInt("idSerie"), rs.getString("imagenUrl"), rs.getInt("esBaja"));
                if (personaje.getEsBaja() == 0 && sdao.getSeriePorId(personaje.getIdSerie()).getEsBaja() == 0) {
                    personajes.add(personaje);

                }
            }
            sdao.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personajes;

    }

    public List<Personaje> getListaPersonajesAdmin() {
        List<Personaje> personajes = new ArrayList();
        Personaje personaje;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personaje");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                personaje = new Personaje(rs.getInt("id"), rs.getString("nombre"), rs.getInt("idSerie"), rs.getString("imagenUrl"), rs.getInt("esBaja"));
                personajes.add(personaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personajes;

    }

    public void cambiarEstado(int idPersonaje, int valorNuevo) {
        String sql = "UPDATE personaje SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idPersonaje);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean modificarPersonaje(Personaje personaje) {
        boolean resultado = false;
        String sql = "UPDATE personaje SET nombre = ?, imagenUrl = ? WHERE id = ?";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, personaje.getNombre());
            ps.setString(2, personaje.getUrl());

            ps.setInt(3, personaje.getId());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean personajeExiste(String nombre) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personaje where nombre = ?");
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

    public void anadirPersonaje(Personaje personaje) {
        String sql = "INSERT INTO personaje (nombre,imagenUrl, idSerie, esBaja) VALUES (?,?,?,?)";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, personaje.getNombre());
            ps.setString(2, personaje.getUrl());
            System.out.println(personaje.getUrl());
            ps.setInt(3, personaje.getIdSerie());
            ps.setInt(4, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
