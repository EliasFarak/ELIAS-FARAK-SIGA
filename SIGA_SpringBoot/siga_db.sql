-- ============================================================
-- SIGA - Script de base de datos
-- GA7-220501096-AA3-EV01
-- Autor: Elías José Farak Arévalo - GRUPO N° 5 | Ficha 3070323
-- ============================================================

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS siga_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE siga_db;

-- Crear tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario          INT           NOT NULL AUTO_INCREMENT,
    nombre_usuario      VARCHAR(50)   NOT NULL,
    apellido_usuario    VARCHAR(50)   NOT NULL,
    correo_usuario      VARCHAR(100)  NOT NULL UNIQUE,
    contrasena_usuario  VARCHAR(255)  NOT NULL,
    rol_usuario         VARCHAR(20)   NOT NULL DEFAULT 'CONSULTA',
    estado_usuario      VARCHAR(10)   NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT pk_usuario PRIMARY KEY (id_usuario)
);

-- Datos de prueba
INSERT IGNORE INTO usuarios (nombre_usuario, apellido_usuario, correo_usuario, contrasena_usuario, rol_usuario, estado_usuario)
VALUES
    ('Elias',    'Farak',     'elias.farak@siga.com',    'siga2026', 'ADMIN',    'ACTIVO'),
    ('Diego',    'Alzate',    'diego.alzate@siga.com',   'siga2026', 'OPERADOR', 'ACTIVO'),
    ('Cristian', 'Mozo',      'cristian.mozo@siga.com',  'siga2026', 'OPERADOR', 'ACTIVO'),
    ('Juan',     'Rodriguez', 'juan.rodriguez@siga.com', 'siga2026', 'CONSULTA', 'ACTIVO');
