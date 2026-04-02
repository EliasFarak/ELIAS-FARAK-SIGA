-- ============================================================
-- SCRIPT SQL - BASE DE DATOS SIGA
-- Sistema de Gestión Integral
-- Proyecto Formativo SENA - Tecnología en Análisis y Desarrollo de Software
-- Autor: Elías Farak - Diego - Cristian - Juan
-- ============================================================

CREATE DATABASE IF NOT EXISTS siga_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE siga_db;

-- ─── TABLA: usuarios ────────────────────────────────────────
CREATE TABLE IF NOT EXISTS usuarios (
  id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre          VARCHAR(100) NOT NULL,
  correo          VARCHAR(150) NOT NULL UNIQUE,
  contrasena      VARCHAR(255) NOT NULL,
  rol             ENUM('admin','supervisor','usuario') NOT NULL DEFAULT 'usuario',
  estado          ENUM('activo','inactivo') NOT NULL DEFAULT 'activo',
  fecha_creacion  DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ─── TABLA: procesos ────────────────────────────────────────
CREATE TABLE IF NOT EXISTS procesos (
  id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre          VARCHAR(150) NOT NULL,
  descripcion     TEXT,
  responsable_id  INT UNSIGNED,
  estado          ENUM('activo','inactivo') NOT NULL DEFAULT 'activo',
  fecha_creacion  DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (responsable_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- ─── TABLA: registros ───────────────────────────────────────
CREATE TABLE IF NOT EXISTS registros (
  id              INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  titulo          VARCHAR(200) NOT NULL,
  descripcion     TEXT,
  proceso_id      INT UNSIGNED NOT NULL,
  usuario_id      INT UNSIGNED NOT NULL,
  estado          ENUM('pendiente','en_proceso','completado','cancelado') NOT NULL DEFAULT 'pendiente',
  fecha_creacion  DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (proceso_id) REFERENCES procesos(id) ON DELETE CASCADE,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- ─── DATOS DE PRUEBA ────────────────────────────────────────
-- Contraseña para todos los usuarios de prueba: Admin123*
INSERT INTO usuarios (nombre, correo, contrasena, rol) VALUES
('Administrador SIGA', 'admin@siga.com', '$2a$10$xJ/8zQkV3mL2EgXwP5RnHOtY6DfMqB1sKpCdNuIvWrAeZ4h7GjT0i', 'admin'),
('Supervisor General', 'supervisor@siga.com', '$2a$10$xJ/8zQkV3mL2EgXwP5RnHOtY6DfMqB1sKpCdNuIvWrAeZ4h7GjT0i', 'supervisor'),
('Usuario Prueba', 'usuario@siga.com', '$2a$10$xJ/8zQkV3mL2EgXwP5RnHOtY6DfMqB1sKpCdNuIvWrAeZ4h7GjT0i', 'usuario');

INSERT INTO procesos (nombre, descripcion, responsable_id, estado) VALUES
('Gestión de Nómina', 'Proceso encargado del control y pago de nómina del personal.', 1, 'activo'),
('Control de Inventario', 'Seguimiento y administración de recursos materiales.', 2, 'activo'),
('Atención al Cliente', 'Registro y seguimiento de solicitudes de clientes.', 2, 'activo');

INSERT INTO registros (titulo, descripcion, proceso_id, usuario_id, estado) VALUES
('Pago nómina enero', 'Registro del pago de nómina correspondiente al mes de enero.', 1, 3, 'completado'),
('Inventario bodega A', 'Conteo y verificación de existencias en bodega A.', 2, 3, 'en_proceso'),
('Solicitud cliente #001', 'Solicitud de soporte técnico de cliente corporativo.', 3, 3, 'pendiente');
