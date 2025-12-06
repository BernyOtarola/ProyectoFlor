package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/reservas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; 

    /**
     * Obtiene una conexión a la base de datos
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection cn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión establecida con BD Reservas");
            return cn;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con BD: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Prueba la conexión a la base de datos
     * @return 
     */
    public static boolean probarConexion() {
        try (Connection cn = getConnection()) {
            return cn != null && !cn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Fallo en prueba de conexión: " + e.getMessage());
            return false;
        }
    }
}