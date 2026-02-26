package com.siga.modulo.usuarios;

import com.siga.vista.MenuUsuarios;

/**
 * Clase principal del módulo de Gestión de Usuarios del sistema SIGA.
 * Punto de entrada de la aplicación.
 *
 * Proyecto  : SIGA - Sistema Integrado de Gestión Administrativa
 * Módulo    : Gestión de Usuarios
 * Evidencia : GA7-220501096-AA2-EV01
 * Programa  : Tecnólogo en Análisis y Desarrollo de Software - SENA
 * Ficha     : 3070323
 *
 * @author Elías José Farak Arévalo
 * @version 1.0
 * @since 2026
 */
public class Main {

    /**
     * Método principal que inicia la ejecución del módulo de usuarios.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     SIGA - Sistema Integrado de Gestión      ║");
        System.out.println("║           Módulo de Usuarios v1.0            ║");
        System.out.println("║    SENA - ADSO - Ficha 3070323 - 2026        ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        MenuUsuarios menuPrincipal = new MenuUsuarios();
        menuPrincipal.mostrarMenu();
    }
}
