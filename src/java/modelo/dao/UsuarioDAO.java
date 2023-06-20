/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

/**
 *
 * @author Angel
 */
import modelo.entidades.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import modelo.entidades.Usuario;

public class UsuarioDAO {

    private Connection conexion;

    public UsuarioDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Usuario getUsuario(String email) {
        Usuario usuario = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuario WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getString("nombre"), rs.getString("apellidos"), rs.getString("contrasena"), rs.getString("email"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("rol"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario getUsuarioPorId(int id) {
        Usuario usuario = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuario WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("contrasena"), rs.getString("email"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("rol"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> getListaUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        String sql = "SELECT * FROM usuario";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setContra(rs.getString("contrasena"));
                usuario.setEmail(rs.getString("email"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEsBaja(rs.getInt("esBaja"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean usuarioExiste(String email) {
        boolean res = true;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT id FROM usuario WHERE LOWER(email) = ?");
            ps.setString(1, email.toLowerCase());
            ResultSet rs = ps.executeQuery();
            res = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    // Método para agregar un nuevo usuario
    public boolean anadirUsuario(Usuario usuario) {
        boolean resultado = false;

        String sql = "INSERT INTO usuario (nombre, apellidos, contrasena, email, direccion,telefono, rol, esBaja) VALUES (?, ?, ?, ?, ?, ?, ?,0)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getContra());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getDireccion());
            ps.setString(6, usuario.getTelefono());
            ps.setString(7, usuario.getRol());
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void modificarDireccion(int id, String direccion) {
        String sql = "UPDATE usuario SET direccion = ? WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, direccion);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarContrasena(int id, String contrasena) {
        String sql = "UPDATE usuario SET contrasena = ? WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, contrasena);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar un usuario existente
    public boolean actualizarUsuario(Usuario usuario) {
        boolean resultado = false;
        String sql = "UPDATE usuario SET nombre = ?, apellidos = ?, email = ?, direccion = ?, telefono = ?, rol = ?, contrasena = ? WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getRol());
            ps.setString(7, usuario.getContra());
            ps.setInt(8, usuario.getId());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void cambiarEstado(int idUsuario, int valorNuevo) {
        String sql = "UPDATE usuario SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarDireccion(String direccion, int idUsuario) {
        String sql = "UPDATE usuario SET direccion = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, direccion);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
