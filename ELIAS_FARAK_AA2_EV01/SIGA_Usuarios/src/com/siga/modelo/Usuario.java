package com.siga.modelo;

/**
 * Clase que representa la entidad Usuario dentro del sistema SIGA.
 * Contiene los atributos principales de un usuario registrado en el sistema.
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public class Usuario {

    private int    idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;
    private String rolUsuario;
    private String estadoUsuario;

    /**
     * Constructor vacío requerido para instanciación por DAO.
     */
    public Usuario() {}

    /**
     * Constructor completo para crear un usuario con todos sus atributos.
     *
     * @param idUsuario        identificador único del usuario
     * @param nombreUsuario    nombre del usuario
     * @param apellidoUsuario  apellido del usuario
     * @param correoUsuario    correo electrónico del usuario
     * @param contrasenaUsuario contraseña del usuario
     * @param rolUsuario       rol asignado (ADMIN, OPERADOR, CONSULTA)
     * @param estadoUsuario    estado del usuario (ACTIVO, INACTIVO)
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    /**
     * Representación en texto del objeto Usuario para depuración.
     *
     * @return cadena con los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
               "idUsuario="         + idUsuario         +
               ", nombreUsuario='"  + nombreUsuario      + '\'' +
               ", apellidoUsuario='"+ apellidoUsuario    + '\'' +
               ", correoUsuario='"  + correoUsuario      + '\'' +
               ", rolUsuario='"     + rolUsuario         + '\'' +
               ", estadoUsuario='"  + estadoUsuario      + '\'' +
               '}';
    }
}
