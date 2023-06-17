
package modelo.entidades;

/**
 *
 * @author Angel
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection conexion;

   public Conexion() {
    try {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        // Establecer la codificación de caracteres a UTF-8
        String url = "jdbc:mysql://localhost:3306/Figurama?useUnicode=true&characterEncoding=UTF-8";
        conexion = DriverManager.getConnection(url, "root", "");

    } catch (SQLException e) {
        System.err.println("Error al establecer la conexión: " + e.getMessage());
    }
}

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
