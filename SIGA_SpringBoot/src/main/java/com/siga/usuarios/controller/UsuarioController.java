package com.siga.usuarios.controller;

import com.siga.usuarios.model.Usuario;
import com.siga.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador Spring MVC que gestiona las peticiones HTTP del módulo de Usuarios.
 * Maneja las rutas de inserción, consulta, actualización y eliminación.
 *
 * @author Elías Farak - Grupo N° 5
 * @version 1.1
 * @since Marzo 2026
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    /** Servicio de usuarios inyectado por Spring */
    @Autowired
    private UsuarioService usuarioService;

    // ========================== CONSULTAR TODOS ==========================

    /**
     * Muestra la lista de todos los usuarios registrados en el sistema.
     * Método HTTP: GET
     *
     * @param modelo objeto Model para pasar datos a la vista
     * @return nombre de la plantilla Thymeleaf a renderizar
     */
    @GetMapping
    public String listarUsuarios(Model modelo) {
        // Obtener todos los usuarios y pasarlos a la vista
        modelo.addAttribute("listaUsuarios", usuarioService.obtenerTodosUsuarios());
        modelo.addAttribute("titulo", "Gestión de Usuarios - SIGA");
        return "usuarios/lista";
    }

    // ========================== FORMULARIO NUEVO ==========================

    /**
     * Muestra el formulario para registrar un nuevo usuario.
     * Método HTTP: GET
     *
     * @param modelo objeto Model para pasar un usuario vacío al formulario
     * @return nombre de la plantilla del formulario
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model modelo) {
        // Pasar un usuario vacío para enlazar con el formulario
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("titulo", "Nuevo Usuario - SIGA");
        modelo.addAttribute("accion", "Registrar");
        return "usuarios/formulario";
    }

    // ========================== INSERTAR ==========================

    /**
     * Recibe los datos del formulario y guarda el nuevo usuario en la base de datos.
     * Método HTTP: POST
     *
     * @param usuario objeto Usuario con los datos del formulario
     * @return redirección a la lista de usuarios
     */
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // Guardar el nuevo usuario en la base de datos
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // ========================== FORMULARIO EDITAR ==========================

    /**
     * Muestra el formulario precargado con los datos del usuario a editar.
     * Método HTTP: GET
     *
     * @param idUsuario identificador del usuario a editar
     * @param modelo    objeto Model para pasar los datos a la vista
     * @return nombre de la plantilla del formulario o redirección si no existe
     */
    @GetMapping("/editar/{idUsuario}")
    public String mostrarFormularioEditar(@PathVariable int idUsuario, Model modelo) {
        // Buscar el usuario por ID
        Optional<Usuario> usuarioEncontrado = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (usuarioEncontrado.isPresent()) {
            modelo.addAttribute("usuario", usuarioEncontrado.get());
            modelo.addAttribute("titulo", "Editar Usuario - SIGA");
            modelo.addAttribute("accion", "Actualizar");
            return "usuarios/formulario";
        }

        // Si no existe el usuario, redirigir a la lista
        return "redirect:/usuarios";
    }

    // ========================== ACTUALIZAR ==========================

    /**
     * Recibe los datos del formulario de edición y actualiza el usuario en la base de datos.
     * Método HTTP: POST
     *
     * @param usuario objeto Usuario con los datos actualizados
     * @return redirección a la lista de usuarios
     */
    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // Verificar que el usuario existe antes de actualizar
        if (usuarioService.existeUsuario(usuario.getIdUsuario())) {
            usuarioService.guardarUsuario(usuario);
        }
        return "redirect:/usuarios";
    }

    // ========================== ELIMINAR ==========================

    /**
     * Elimina un usuario de la base de datos según su identificador.
     * Método HTTP: GET
     *
     * @param idUsuario identificador del usuario a eliminar
     * @return redirección a la lista de usuarios
     */
    @GetMapping("/eliminar/{idUsuario}")
    public String eliminarUsuario(@PathVariable int idUsuario) {
        // Verificar existencia antes de eliminar
        if (usuarioService.existeUsuario(idUsuario)) {
            usuarioService.eliminarUsuario(idUsuario);
        }
        return "redirect:/usuarios";
    }
}
