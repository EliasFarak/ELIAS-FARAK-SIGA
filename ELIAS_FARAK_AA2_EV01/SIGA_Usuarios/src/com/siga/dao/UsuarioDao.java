package com.siga.dao;

import com.siga.conexion.ConexionDB;
import com.siga.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO (Data Access Object) para la entidad Usuario.
 * Gestiona todas las operaciones CRUD sobre la tabla 'usuarios' mediante JDBC.
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public class UsuarioDao implements IUsuarioDao {

    // ========================== SENTENCIAS SQL ==========================

    private static final String SQL_INSERTAR =
        "INSERT INTO usuarios (nombre_usuario, apellido_usuario, correo_usuario, " +
        "contrasena_usuario, rol_usuario, estado_usuario) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
        "SELECT id_usuario, nombre_usuario, apellido_usuario, correo_usuario, " +
        "contrasena_usuario, rol_usuario, estado_usuario FROM usuarios";

    private static final String SQL_CONSULTAR_POR_ID =
        "SELECT id_usuario, nombre_usuario, apellido_usuario, correo_usuario, " +
        "contrasena_usuario, rol_usuario, estado_usuario FROM usuarios WHERE id_usuario = ?";

    private static final String SQL_ACTUALIZAR =
        "UPDATE usuarios SET nombre_usuario = ?, apellido_usuario = ?, " +
        "correo_usuario = ?, contrasena_usuario = ?, rol_usuario = ?, " +
        "estado_usuario = ? WHERE id_usuario = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM usuarios WHERE id_usuario = ?";

    // ========================== OPERACIONES CRUD ==========================

    /**
     * {@inheritDoc}
     *
     * Inserta un nuevo usuario en la tabla 'usuarios' de la base de datos.
     */
    @Override
    public boolean insertarUsuario(Usuario usuario) {
        boolean operacionExitosa = false;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

            sentencia.setString(1, usuario.getNombreUsuario());
            sentencia.setString(2, usuario.getApellidoUsuario());
            sentencia.setString(3, usuario.getCorreoUsuario());
            sentencia.setString(4, usuario.getContrasenaUsuario());
            sentencia.setString(5, usuario.getRolUsuario());
            sentencia.setString(6, usuario.getEstadoUsuario());

            int filasAfectadas = sentencia.executeUpdate();
            operacionExitosa = filasAfectadas > 0;

            if (operacionExitosa) {
                System.out.println("[SIGA] Usuario insertado correctamente: " + usuario.getNombreUsuario());
            }

        } catch (SQLException e) {
            System.err.println("[SIGA] Error al insertar usuario: " + e.getMessage());
        }

        return operacionExitosa;
    }

    /**
     * {@inheritDoc}
     *
     * Consulta y retorna todos los usuarios almacenados en la base de datos.
     */
    @Override
    public List<Usuario> consultarTodosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultados = sentencia.executeQuery()) {

            while (resultados.next()) {
                Usuario usuario = mapearResultadoAUsuario(resultados);
                listaUsuarios.add(usuario);
            }

            System.out.println("[SIGA] Total usuarios consultados: " + listaUsuarios.size());

        } catch (SQLException e) {
            System.err.println("[SIGA] Error al consultar usuarios: " + e.getMessage());
        }

        return listaUsuarios;
    }

    /**
     * {@inheritDoc}
     *
     * Consulta un usuario por su ID único en la base de datos.
     */
    @Override
    public Usuario consultarUsuarioPorId(int idUsuario) {
        Usuario usuarioEncontrado = null;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            sentencia.setInt(1, idUsuario);

            try (ResultSet resultados = sentencia.executeQuery()) {
                if (resultados.next()) {
                    usuarioEncontrado = mapearResultadoAUsuario(resultados);
                    System.out.println("[SIGA] Usuario encontrado: " + usuarioEncontrado.getNombreUsuario());
                } else {
                    System.out.println("[SIGA] No se encontró usuario con ID: " + idUsuario);
                }
            }

        } catch (SQLException e) {
            System.err.println("[SIGA] Error al consultar usuario por ID: " + e.getMessage());
        }

        return usuarioEncontrado;
    }

    /**
     * {@inheritDoc}
     *
     * Actualiza todos los campos de un usuario existente en la base de datos.
     */
    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        boolean operacionExitosa = false;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            sentencia.setString(1, usuario.getNombreUsuario());
            sentencia.setString(2, usuario.getApellidoUsuario());
            sentencia.setString(3, usuario.getCorreoUsuario());
            sentencia.setString(4, usuario.getContrasenaUsuario());
            sentencia.setString(5, usuario.getRolUsuario());
            sentencia.setString(6, usuario.getEstadoUsuario());
            sentencia.setInt(7, usuario.getIdUsuario());

            int filasAfectadas = sentencia.executeUpdate();
            operacionExitosa = filasAfectadas > 0;

            if (operacionExitosa) {
                System.out.println("[SIGA] Usuario actualizado correctamente. ID: " + usuario.getIdUsuario());
            } else {
                System.out.println("[SIGA] No se encontró el usuario a actualizar. ID: " + usuario.getIdUsuario());
            }

        } catch (SQLException e) {
            System.err.println("[SIGA] Error al actualizar usuario: " + e.getMessage());
        }

        return operacionExitosa;
    }

    /**
     * {@inheritDoc}
     *
     * Elimina un usuario de la base de datos según su identificador único.
     */
    @Override
    public boolean eliminarUsuario(int idUsuario) {
        boolean operacionExitosa = false;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, idUsuario);

            int filasAfectadas = sentencia.executeUpdate();
            operacionExitosa = filasAfectadas > 0;

            if (operacionExitosa) {
                System.out.println("[SIGA] Usuario eliminado correctamente. ID: " + idUsuario);
            } else {
                System.out.println("[SIGA] No se encontró el usuario a eliminar. ID: " + idUsuario);
            }

        } catch (SQLException e) {
            System.err.println("[SIGA] Error al eliminar usuario: " + e.getMessage());
        }

        return operacionExitosa;
    }

    // ========================== MÉTODOS AUXILIARES ==========================

    /**
     * Mapea una fila del ResultSet a un objeto Usuario.
     *
     * @param resultados ResultSet con la fila a convertir
     * @return objeto Usuario con los datos de la fila
     * @throws SQLException si ocurre un error al leer el ResultSet
     */
    private Usuario mapearResultadoAUsuario(ResultSet resultados) throws SQLException {
        return new Usuario(
            resultados.getInt("id_usuario"),
            resultados.getString("nombre_usuario"),
            resultados.getString("apellido_usuario"),
            resultados.getString("correo_usuario"),
            resultados.getString("contrasena_usuario"),
            resultados.getString("rol_usuario"),
            resultados.getString("estado_usuario")
        );
    }
}
