package com.siga.vista;

import com.siga.dao.UsuarioDao;
import com.siga.modelo.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la interfaz de consola del módulo de Usuarios del sistema SIGA.
 * Permite al operador ejecutar operaciones CRUD de forma interactiva desde la terminal.
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public class MenuUsuarios {

    private final UsuarioDao usuarioDao;
    private final Scanner    lectorEntrada;

    // Constantes del menú
    private static final int OPCION_INSERTAR      = 1;
    private static final int OPCION_CONSULTAR_ALL = 2;
    private static final int OPCION_CONSULTAR_ID  = 3;
    private static final int OPCION_ACTUALIZAR    = 4;
    private static final int OPCION_ELIMINAR      = 5;
    private static final int OPCION_SALIR         = 6;

    /**
     * Constructor que inicializa el DAO y el lector de entrada del usuario.
     */
    public MenuUsuarios() {
        this.usuarioDao    = new UsuarioDao();
        this.lectorEntrada = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal e inicia el ciclo de interacción con el usuario.
     */
    public void mostrarMenu() {
        int opcionSeleccionada = 0;

        do {
            imprimirEncabezadoMenu();

            try {
                opcionSeleccionada = Integer.parseInt(lectorEntrada.nextLine().trim());
                procesarOpcion(opcionSeleccionada);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Opción inválida. Por favor ingrese un número del 1 al 6.");
            }

        } while (opcionSeleccionada != OPCION_SALIR);

        lectorEntrada.close();
    }

    /**
     * Imprime el encabezado y las opciones del menú principal en consola.
     */
    private void imprimirEncabezadoMenu() {
        System.out.println("\n  ╔═══════════════════════════════════════╗");
        System.out.println("  ║     SIGA - MÓDULO DE USUARIOS          ║");
        System.out.println("  ╠═══════════════════════════════════════╣");
        System.out.println("  ║  1. Insertar nuevo usuario             ║");
        System.out.println("  ║  2. Consultar todos los usuarios       ║");
        System.out.println("  ║  3. Consultar usuario por ID           ║");
        System.out.println("  ║  4. Actualizar usuario                 ║");
        System.out.println("  ║  5. Eliminar usuario                   ║");
        System.out.println("  ║  6. Salir                              ║");
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.print("  Seleccione una opción: ");
    }

    /**
     * Dirige la ejecución hacia el método correspondiente según la opción elegida.
     *
     * @param opcion número de opción seleccionada por el usuario
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case OPCION_INSERTAR:
                ejecutarInsercionUsuario();
                break;
            case OPCION_CONSULTAR_ALL:
                ejecutarConsultaGeneral();
                break;
            case OPCION_CONSULTAR_ID:
                ejecutarConsultaPorId();
                break;
            case OPCION_ACTUALIZAR:
                ejecutarActualizacionUsuario();
                break;
            case OPCION_ELIMINAR:
                ejecutarEliminacionUsuario();
                break;
            case OPCION_SALIR:
                System.out.println("\n  [SIGA] Cerrando módulo de usuarios. ¡Hasta luego!\n");
                break;
            default:
                System.out.println("  [!] Opción no válida. Ingrese un número del 1 al 6.");
        }
    }

    // ========================== MÉTODOS DE OPERACIONES ==========================

    /**
     * Solicita los datos necesarios e inserta un nuevo usuario en la base de datos.
     */
    private void ejecutarInsercionUsuario() {
        System.out.println("\n  --- INSERTAR NUEVO USUARIO ---");
        Usuario nuevoUsuario = new Usuario();

        System.out.print("  Nombre      : ");
        nuevoUsuario.setNombreUsuario(lectorEntrada.nextLine());

        System.out.print("  Apellido    : ");
        nuevoUsuario.setApellidoUsuario(lectorEntrada.nextLine());

        System.out.print("  Correo      : ");
        nuevoUsuario.setCorreoUsuario(lectorEntrada.nextLine());

        System.out.print("  Contraseña  : ");
        nuevoUsuario.setContrasenaUsuario(lectorEntrada.nextLine());

        System.out.print("  Rol (ADMIN/OPERADOR/CONSULTA): ");
        nuevoUsuario.setRolUsuario(lectorEntrada.nextLine().toUpperCase());

        System.out.print("  Estado (ACTIVO/INACTIVO)     : ");
        nuevoUsuario.setEstadoUsuario(lectorEntrada.nextLine().toUpperCase());

        boolean resultado = usuarioDao.insertarUsuario(nuevoUsuario);
        System.out.println(resultado
            ? "  ✔ Usuario insertado exitosamente."
            : "  ✘ No se pudo insertar el usuario.");
    }

    /**
     * Consulta y muestra todos los usuarios registrados en el sistema.
     */
    private void ejecutarConsultaGeneral() {
        System.out.println("\n  --- LISTADO DE USUARIOS REGISTRADOS ---");
        List<Usuario> listaUsuarios = usuarioDao.consultarTodosUsuarios();

        if (listaUsuarios.isEmpty()) {
            System.out.println("  [!] No hay usuarios registrados en el sistema.");
        } else {
            imprimirEncabezadoTabla();
            for (Usuario usuario : listaUsuarios) {
                imprimirFilaUsuario(usuario);
            }
            System.out.println("  " + "-".repeat(85));
        }
    }

    /**
     * Solicita un ID y muestra el usuario correspondiente si existe.
     */
    private void ejecutarConsultaPorId() {
        System.out.println("\n  --- CONSULTAR USUARIO POR ID ---");
        System.out.print("  Ingrese el ID del usuario: ");

        try {
            int idBuscado = Integer.parseInt(lectorEntrada.nextLine().trim());
            Usuario usuarioEncontrado = usuarioDao.consultarUsuarioPorId(idBuscado);

            if (usuarioEncontrado != null) {
                imprimirEncabezadoTabla();
                imprimirFilaUsuario(usuarioEncontrado);
                System.out.println("  " + "-".repeat(85));
            } else {
                System.out.println("  [!] No se encontró ningún usuario con ID: " + idBuscado);
            }

        } catch (NumberFormatException e) {
            System.out.println("  [!] El ID debe ser un número entero válido.");
        }
    }

    /**
     * Solicita el ID y los nuevos datos para actualizar un usuario existente.
     */
    private void ejecutarActualizacionUsuario() {
        System.out.println("\n  --- ACTUALIZAR USUARIO ---");
        System.out.print("  Ingrese el ID del usuario a actualizar: ");

        try {
            int idActualizar = Integer.parseInt(lectorEntrada.nextLine().trim());
            Usuario usuarioExistente = usuarioDao.consultarUsuarioPorId(idActualizar);

            if (usuarioExistente == null) {
                System.out.println("  [!] No existe un usuario con ID: " + idActualizar);
                return;
            }

            System.out.println("  Usuario actual: " + usuarioExistente);
            System.out.println("  Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");

            System.out.print("  Nuevo nombre [" + usuarioExistente.getNombreUsuario() + "]: ");
            String nuevoNombre = lectorEntrada.nextLine();
            if (!nuevoNombre.isBlank()) usuarioExistente.setNombreUsuario(nuevoNombre);

            System.out.print("  Nuevo apellido [" + usuarioExistente.getApellidoUsuario() + "]: ");
            String nuevoApellido = lectorEntrada.nextLine();
            if (!nuevoApellido.isBlank()) usuarioExistente.setApellidoUsuario(nuevoApellido);

            System.out.print("  Nuevo correo [" + usuarioExistente.getCorreoUsuario() + "]: ");
            String nuevoCorreo = lectorEntrada.nextLine();
            if (!nuevoCorreo.isBlank()) usuarioExistente.setCorreoUsuario(nuevoCorreo);

            System.out.print("  Nueva contraseña: ");
            String nuevaContrasena = lectorEntrada.nextLine();
            if (!nuevaContrasena.isBlank()) usuarioExistente.setContrasenaUsuario(nuevaContrasena);

            System.out.print("  Nuevo rol [" + usuarioExistente.getRolUsuario() + "]: ");
            String nuevoRol = lectorEntrada.nextLine();
            if (!nuevoRol.isBlank()) usuarioExistente.setRolUsuario(nuevoRol.toUpperCase());

            System.out.print("  Nuevo estado [" + usuarioExistente.getEstadoUsuario() + "]: ");
            String nuevoEstado = lectorEntrada.nextLine();
            if (!nuevoEstado.isBlank()) usuarioExistente.setEstadoUsuario(nuevoEstado.toUpperCase());

            boolean resultado = usuarioDao.actualizarUsuario(usuarioExistente);
            System.out.println(resultado
                ? "  ✔ Usuario actualizado exitosamente."
                : "  ✘ No se pudo actualizar el usuario.");

        } catch (NumberFormatException e) {
            System.out.println("  [!] El ID debe ser un número entero válido.");
        }
    }

    /**
     * Solicita el ID del usuario a eliminar y ejecuta la operación tras confirmación.
     */
    private void ejecutarEliminacionUsuario() {
        System.out.println("\n  --- ELIMINAR USUARIO ---");
        System.out.print("  Ingrese el ID del usuario a eliminar: ");

        try {
            int idEliminar = Integer.parseInt(lectorEntrada.nextLine().trim());
            Usuario usuarioAEliminar = usuarioDao.consultarUsuarioPorId(idEliminar);

            if (usuarioAEliminar == null) {
                System.out.println("  [!] No existe un usuario con ID: " + idEliminar);
                return;
            }

            System.out.println("  Usuario a eliminar: " + usuarioAEliminar.getNombreUsuario()
                               + " " + usuarioAEliminar.getApellidoUsuario());
            System.out.print("  ¿Confirma la eliminación? (S/N): ");
            String confirmacion = lectorEntrada.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                boolean resultado = usuarioDao.eliminarUsuario(idEliminar);
                System.out.println(resultado
                    ? "  ✔ Usuario eliminado exitosamente."
                    : "  ✘ No se pudo eliminar el usuario.");
            } else {
                System.out.println("  [!] Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("  [!] El ID debe ser un número entero válido.");
        }
    }

    // ========================== MÉTODOS DE VISUALIZACIÓN ==========================

    /**
     * Imprime el encabezado de la tabla de usuarios en consola.
     */
    private void imprimirEncabezadoTabla() {
        System.out.println("  " + "-".repeat(85));
        System.out.printf("  %-5s %-15s %-15s %-25s %-10s %-10s%n",
            "ID", "NOMBRE", "APELLIDO", "CORREO", "ROL", "ESTADO");
        System.out.println("  " + "-".repeat(85));
    }

    /**
     * Imprime una fila de datos correspondiente a un usuario.
     *
     * @param usuario objeto Usuario a mostrar
     */
    private void imprimirFilaUsuario(Usuario usuario) {
        System.out.printf("  %-5d %-15s %-15s %-25s %-10s %-10s%n",
            usuario.getIdUsuario(),
            usuario.getNombreUsuario(),
            usuario.getApellidoUsuario(),
            usuario.getCorreoUsuario(),
            usuario.getRolUsuario(),
            usuario.getEstadoUsuario()
        );
    }
}
