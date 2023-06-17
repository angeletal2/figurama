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
import modelo.entidades.figura.Proveedor;

/**
 *
 * @author Angel
 */
public class ProveedorDAO {

    private Connection conexion;

    public ProveedorDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    //Método para obtener los datos de un proveedor en base a su id
    public Proveedor getProveedorPorId(int id) {
        Proveedor proveedor = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM proveedor WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor(id, rs.getString("nombre"), rs.getString("imagenUrl"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    //Método que devuelve una lista con todos los proveedores existentes
    public List<Proveedor> getListaProveedores() {
        List<Proveedor> proveedores = new ArrayList();
        Proveedor proveedor;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM proveedor");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                proveedor = new Proveedor(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagenUrl"), rs.getInt("esBaja"));
                if (proveedor.getEsBaja() == 0) {
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    //Método que devuelve una lista con todos los proveedores existentes
    public List<Proveedor> getListaProveedoresAdmin() {
        List<Proveedor> proveedores = new ArrayList();
        Proveedor proveedor;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM proveedor");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                proveedor = new Proveedor(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagenUrl"), rs.getInt("esBaja"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    //Método para obtener los datos de un proveedor en base a su nombre
    public Proveedor getProveedorPorNombre(String nombre) {
        Proveedor proveedor = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM proveedor WHERE LOWER(nombre) = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagenURL"), rs.getInt("esBaja"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    public void cambiarEstado(int idProveedor, int valorNuevo) {
        String sql = "UPDATE proveedor SET esBaja = ? WHERE id = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, valorNuevo);
            ps.setInt(2, idProveedor);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean modificarProveedor(Proveedor proveedor) {
        boolean resultado = false;
        String sql = "UPDATE proveedor SET nombre = ?, imagenUrl = ? WHERE id = ?";
        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getUrl());
            ps.setInt(3, proveedor.getId());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean proveedorExiste(String nombre) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM proveedor where nombre = ?");
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

    public void anadirProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (nombre,imagenUrl,esBaja) VALUES (?,?,?)";

        try {

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getUrl());
            ps.setInt(3, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
