package com.siga.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del sistema SIGA - Módulo de Gestión de Usuarios.
 * Punto de entrada de la aplicación Spring Boot.
 *
 * Proyecto  : SIGA - Sistema Integrado de Gestión Administrativa
 * Evidencia : GA7-220501096-AA3-EV01
 * Programa  : Tecnólogo en Análisis y Desarrollo de Software - SENA
 * Ficha     : 3070323
 *
 * @author Elías Farak - Grupo N° 5
 * @version 1.1
 * @since MAEZO 2026
 */
@SpringBootApplication
public class SigaUsuariosApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(SigaUsuariosApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  SIGA - Módulo de Usuarios iniciado");
        System.out.println("  Acceder en: http://localhost:8080/usuarios");
        System.out.println("  SENA - ADSO - Ficha 3070323 - 2026");
        System.out.println("==============================================");
    }
}
