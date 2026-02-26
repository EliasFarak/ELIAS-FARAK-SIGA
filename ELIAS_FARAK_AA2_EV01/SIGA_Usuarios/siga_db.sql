-- ============================================================
-- SIGA - Sistema Integrado de Gestión Administrativa
-- Script de creación de base de datos y tabla usuarios
-- Evidencia: GA7-220501096-AA2-EV01
-- Autor: Elías José Farak Arévalo
-- Ficha: 3070323 | ADSO - SENA | 2026
-- ============================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS siga_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

-- Usar la base de datos
USE siga_db;

-- Crear la tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario          INT           NOT NULL AUTO_INCREMENT,
    nombre_usuario      VARCHAR(50)   NOT NULL,
    apellido_usuario    VARCHAR(50)   NOT NULL,
    correo_usuario      VARCHAR(100)  NOT NULL UNIQUE,
    contrasena_usuario  VARCHAR(255)  NOT NULL,
    rol_usuario         ENUM('ADMIN', 'OPERADOR', 'CONSULTA') NOT NULL DEFAULT 'CONSULTA',
    estado_usuario      ENUM('ACTIVO', 'INACTIVO')            NOT NULL DEFAULT 'ACTIVO',
    fecha_creacion      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuario PRIMARY KEY (id_usuario)
);

-- Insertar datos de prueba
INSERT INTO usuarios (nombre_usuario, apellido_usuario, correo_usuario, contrasena_usuario, rol_usuario, estado_usuario)
VALUES
    ('Elias',   'Farak',    'elias.farak@siga.com',   'siga2026',  'ADMIN',    'ACTIVO'),
    ('Diego',   'Alzate',   'diego.alzate@siga.com',  'siga2026',  'OPERADOR', 'ACTIVO'),
    ('Cristian','Mozo',     'cristian.mozo@siga.com', 'siga2026',  'OPERADOR', 'ACTIVO'),
    ('Juan',    'Rodriguez','juan.rodriguez@siga.com', 'siga2026',  'CONSULTA', 'ACTIVO');

-- Verificar los datos insertados
SELECT * FROM usuarios;
