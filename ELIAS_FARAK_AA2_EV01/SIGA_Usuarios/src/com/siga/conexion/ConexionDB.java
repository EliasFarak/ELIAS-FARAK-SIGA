package com.siga.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de gestionar la conexión con la base de datos MySQL.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public class ConexionDB {

    // Parámetros de conexión a la base de datos
    private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/siga_db";
    private static final String USUARIO_DB   = "root";
    private static final String CONTRASENA_DB = "admin123";
    private static final String DRIVER_MYSQL  = "com.mysql.cj.jdbc.Driver";

    private static Connection conexionActiva = null;

    /**
     * Constructor privado para evitar instanciación directa.
     */
    private ConexionDB() {}

    /**
     * Retorna la conexión activa con la base de datos.
     * Si no existe una conexión abierta, la crea.
     *
     * @return Connection objeto de conexión JDBC
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            if (conexionActiva == null || conexionActiva.isClosed()) {
                Class.forName(DRIVER_MYSQL);
                conexionActiva = DriverManager.getConnection(
                    URL_CONEXION, USUARIO_DB, CONTRASENA_DB
                );
                System.out.println("[SIGA] Conexión establecida con la base de datos.");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
        return conexionActiva;
    }

    /**
     * Cierra la conexión activa con la base de datos.
     */
    public static void cerrarConexion() {
        try {
            if (conexionActiva != null && !conexionActiva.isClosed()) {
                conexionActiva.close();
                System.out.println("[SIGA] Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("[SIGA] Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
