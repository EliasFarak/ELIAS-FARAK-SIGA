package com.siga.usuarios.service;

import com.siga.usuarios.model.Usuario;
import com.siga.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio del módulo de Usuarios.
 * Actúa como intermediario entre el controlador y el repositorio.
 *
 * @author Elías Farak - GRUPO # 5
 * @version 1.1
 * @since MARZO 2026
 */
@Service
public class UsuarioService {

    /** Repositorio inyectado automáticamente por Spring */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene la lista completa de usuarios registrados en el sistema.
     *
     * @return lista de todos los usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param idUsuario identificador del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    public Optional<Usuario> obtenerUsuarioPorId(int idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente en la base de datos.
     *
     * @param usuario objeto Usuario a guardar
     * @return el usuario guardado con su ID generado
     */
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario de la base de datos según su identificador.
     *
     * @param idUsuario identificador del usuario a eliminar
     */
    public void eliminarUsuario(int idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    /**
     * Verifica si existe un usuario con el ID proporcionado.
     *
     * @param idUsuario identificador a verificar
     * @return true si existe, false si no
     */
    public boolean existeUsuario(int idUsuario) {
        return usuarioRepository.existsById(idUsuario);
    }
}
