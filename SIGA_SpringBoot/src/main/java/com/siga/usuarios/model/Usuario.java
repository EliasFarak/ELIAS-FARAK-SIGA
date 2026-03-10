package com.siga.usuarios.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un Usuario dentro del sistema SIGA.
 * Mapeada a la tabla 'usuarios' de la base de datos mediante JPA.
 *
 * @author Elías Farak - Grupo N° 5
 * @version 1.1
 * @since MARZO 2026
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /** Identificador único del usuario, generado automáticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    /** Nombre del usuario */
    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;

    /** Apellido del usuario */
    @Column(name = "apellido_usuario", nullable = false, length = 50)
    private String apellidoUsuario;

    /** Correo electrónico único del usuario */
    @Column(name = "correo_usuario", nullable = false, unique = true, length = 100)
    private String correoUsuario;

    /** Contraseña del usuario */
    @Column(name = "contrasena_usuario", nullable = false, length = 255)
    private String contrasenaUsuario;

    /** Rol asignado al usuario en el sistema */
    @Column(name = "rol_usuario", nullable = false, length = 20)
    private String rolUsuario;

    /** Estado actual del usuario */
    @Column(name = "estado_usuario", nullable = false, length = 10)
    private String estadoUsuario;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {}

    /**
     * Constructor completo para crear un usuario con todos sus atributos.
     *
     * @param idUsuario         identificador único
     * @param nombreUsuario     nombre del usuario
     * @param apellidoUsuario   apellido del usuario
     * @param correoUsuario     correo electrónico
     * @param contrasenaUsuario contraseña
     * @param rolUsuario        rol asignado
     * @param estadoUsuario     estado (ACTIVO/INACTIVO)
     */
    public Usuario(int idUsuario, String nombreUsuario, String apellidoUsuario,
                   String correoUsuario, String contrasenaUsuario,
                   String rolUsuario, String estadoUsuario) {
        this.idUsuario         = idUsuario;
        this.nombreUsuario     = nombreUsuario;
        this.apellidoUsuario   = apellidoUsuario;
        this.correoUsuario     = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.rolUsuario        = rolUsuario;
        this.estadoUsuario     = estadoUsuario;
    }

    // ========================== GETTERS Y SETTERS ==========================

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getApellidoUsuario() { return apellidoUsuario; }
    public void setApellidoUsuario(String apellidoUsuario) { this.apellidoUsuario = apellidoUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public String getContrasenaUsuario() { return contrasenaUsuario; }
    public void setContrasenaUsuario(String contrasenaUsuario) { this.contrasenaUsuario = contrasenaUsuario; }

    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }

    public String getEstadoUsuario() { return estadoUsuario; }
    public void setEstadoUsuario(String estadoUsuario) { this.estadoUsuario = estadoUsuario; }
}
