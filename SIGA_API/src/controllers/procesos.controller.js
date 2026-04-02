// src/controllers/procesos.controller.js
// Controlador CRUD de Procesos Administrativos para SIGA

const db = require('../config/database');

// GET /api/procesos
const listar = async (req, res) => {
  try {
    const [rows] = await db.execute(`
      SELECT p.*, u.nombre AS responsable_nombre
      FROM procesos p
      LEFT JOIN usuarios u ON p.responsable_id = u.id
      ORDER BY p.fecha_creacion DESC
    `);
    res.status(200).json({ success: true, total: rows.length, data: rows });
  } catch (error) {
    console.error('Error al listar procesos:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/procesos/:id
const obtenerPorId = async (req, res) => {
  try {
    const { id } = req.params;
    const [rows] = await db.execute(`
      SELECT p.*, u.nombre AS responsable_nombre
      FROM procesos p
      LEFT JOIN usuarios u ON p.responsable_id = u.id
      WHERE p.id = ?
    `, [id]);

    if (rows.length === 0) {
      return res.status(404).json({ success: false, message: 'Proceso no encontrado.' });
    }

    res.status(200).json({ success: true, data: rows[0] });
  } catch (error) {
    console.error('Error al obtener proceso:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// POST /api/procesos
const crear = async (req, res) => {
  try {
    const { nombre, descripcion, responsable_id, estado = 'activo' } = req.body;

    if (!nombre || !descripcion) {
      return res.status(400).json({
        success: false,
        message: 'Nombre y descripción son campos requeridos.'
      });
    }

    const [result] = await db.execute(
      'INSERT INTO procesos (nombre, descripcion, responsable_id, estado) VALUES (?, ?, ?, ?)',
      [nombre, descripcion, responsable_id || null, estado]
    );

    res.status(201).json({
      success: true,
      message: 'Proceso creado exitosamente.',
      data: { id: result.insertId, nombre, descripcion, responsable_id, estado }
    });
  } catch (error) {
    console.error('Error al crear proceso:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// PUT /api/procesos/:id
const actualizar = async (req, res) => {
  try {
    const { id } = req.params;
    const { nombre, descripcion, responsable_id, estado } = req.body;

    const [existe] = await db.execute('SELECT id FROM procesos WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Proceso no encontrado.' });
    }

    await db.execute(
      'UPDATE procesos SET nombre = ?, descripcion = ?, responsable_id = ?, estado = ? WHERE id = ?',
      [nombre, descripcion, responsable_id, estado, id]
    );

    res.status(200).json({ success: true, message: 'Proceso actualizado correctamente.' });
  } catch (error) {
    console.error('Error al actualizar proceso:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// DELETE /api/procesos/:id
const eliminar = async (req, res) => {
  try {
    const { id } = req.params;

    const [existe] = await db.execute('SELECT id FROM procesos WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Proceso no encontrado.' });
    }

    await db.execute('UPDATE procesos SET estado = "inactivo" WHERE id = ?', [id]);
    res.status(200).json({ success: true, message: 'Proceso eliminado correctamente.' });
  } catch (error) {
    console.error('Error al eliminar proceso:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

module.exports = { listar, obtenerPorId, crear, actualizar, eliminar };
