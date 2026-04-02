// src/controllers/reportes.controller.js
// Controlador de Reportes para SIGA

const db = require('../config/database');

// GET /api/reportes/resumen-general
const resumenGeneral = async (req, res) => {
  try {
    const [[{ totalUsuarios }]] = await db.execute(
      'SELECT COUNT(*) AS totalUsuarios FROM usuarios WHERE estado = "activo"'
    );
    const [[{ totalProcesos }]] = await db.execute(
      'SELECT COUNT(*) AS totalProcesos FROM procesos'
    );
    const [[{ procesosActivos }]] = await db.execute(
      'SELECT COUNT(*) AS procesosActivos FROM procesos WHERE estado = "activo"'
    );
    const [[{ totalRegistros }]] = await db.execute(
      'SELECT COUNT(*) AS totalRegistros FROM registros'
    );

    res.status(200).json({
      success: true,
      data: {
        totalUsuarios,
        totalProcesos,
        procesosActivos,
        procesosInactivos: totalProcesos - procesosActivos,
        totalRegistros
      }
    });
  } catch (error) {
    console.error('Error en reporte resumen:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/reportes/usuarios-por-rol
const usuariosPorRol = async (req, res) => {
  try {
    const [rows] = await db.execute(
      'SELECT rol, COUNT(*) AS cantidad FROM usuarios GROUP BY rol'
    );
    res.status(200).json({ success: true, data: rows });
  } catch (error) {
    console.error('Error en reporte usuarios por rol:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/reportes/procesos-por-estado
const procesosPorEstado = async (req, res) => {
  try {
    const [rows] = await db.execute(
      'SELECT estado, COUNT(*) AS cantidad FROM procesos GROUP BY estado'
    );
    res.status(200).json({ success: true, data: rows });
  } catch (error) {
    console.error('Error en reporte procesos por estado:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

module.exports = { resumenGeneral, usuariosPorRol, procesosPorEstado };
