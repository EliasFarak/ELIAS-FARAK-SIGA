package com.siga.dao;

import com.siga.modelo.Usuario;
import java.util.List;

/**
 * Interfaz que define el contrato de operaciones CRUD para la entidad Usuario.
 * Toda clase que implemente esta interfaz debe proveer las funcionalidades
 * de inserción, consulta, actualización y eliminación.
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public interface IUsuarioDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario objeto Usuario a insertar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean insertarUsuario(Usuario usuario);

    /**
     * Consulta todos los usuarios registrados en la base de datos.
     *
     * @return lista de objetos Usuario
     */
    List<Usuario> consultarTodosUsuarios();

    /**
     * Consulta un usuario específico por su identificador.
     *
     * @param idUsuario identificador único del usuario
     * @return objeto Usuario si se encuentra, null si no existe
     */
    Usuario consultarUsuarioPorId(int idUsuario);

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     *
     * @param usuario objeto Usuario con los datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean actualizarUsuario(Usuario usuario);

    /**
     * Elimina un usuario de la base de datos según su identificador.
     *
     * @param idUsuario identificador único del usuario a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean eliminarUsuario(int idUsuario);
}
