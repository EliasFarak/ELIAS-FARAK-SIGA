// src/controllers/registros.controller.js
// Controlador CRUD de Registros Operativos para SIGA

const db = require('../config/database');

// GET /api/registros
const listar = async (req, res) => {
  try {
    const { proceso_id, estado } = req.query;
    let query = `
      SELECT r.*, p.nombre AS proceso_nombre, u.nombre AS usuario_nombre
      FROM registros r
      LEFT JOIN procesos p ON r.proceso_id = p.id
      LEFT JOIN usuarios u ON r.usuario_id = u.id
      WHERE 1=1
    `;
    const params = [];

    if (proceso_id) { query += ' AND r.proceso_id = ?'; params.push(proceso_id); }
    if (estado)     { query += ' AND r.estado = ?';     params.push(estado); }

    query += ' ORDER BY r.fecha_creacion DESC';

    const [rows] = await db.execute(query, params);
    res.status(200).json({ success: true, total: rows.length, data: rows });
  } catch (error) {
    console.error('Error al listar registros:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/registros/:id
const obtenerPorId = async (req, res) => {
  try {
    const { id } = req.params;
    const [rows] = await db.execute(`
      SELECT r.*, p.nombre AS proceso_nombre, u.nombre AS usuario_nombre
      FROM registros r
      LEFT JOIN procesos p ON r.proceso_id = p.id
      LEFT JOIN usuarios u ON r.usuario_id = u.id
      WHERE r.id = ?
    `, [id]);

    if (rows.length === 0) {
      return res.status(404).json({ success: false, message: 'Registro no encontrado.' });
    }
    res.status(200).json({ success: true, data: rows[0] });
  } catch (error) {
    console.error('Error al obtener registro:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// POST /api/registros
const crear = async (req, res) => {
  try {
    const { titulo, descripcion, proceso_id, estado = 'pendiente' } = req.body;
    const usuario_id = req.usuario.id;

    if (!titulo || !proceso_id) {
      return res.status(400).json({
        success: false,
        message: 'Título y proceso_id son campos requeridos.'
      });
    }

    const [result] = await db.execute(
      'INSERT INTO registros (titulo, descripcion, proceso_id, usuario_id, estado) VALUES (?, ?, ?, ?, ?)',
      [titulo, descripcion, proceso_id, usuario_id, estado]
    );

    res.status(201).json({
      success: true,
      message: 'Registro creado exitosamente.',
      data: { id: result.insertId, titulo, descripcion, proceso_id, usuario_id, estado }
    });
  } catch (error) {
    console.error('Error al crear registro:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// PUT /api/registros/:id
const actualizar = async (req, res) => {
  try {
    const { id } = req.params;
    const { titulo, descripcion, proceso_id, estado } = req.body;

    const [existe] = await db.execute('SELECT id FROM registros WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Registro no encontrado.' });
    }

    await db.execute(
      'UPDATE registros SET titulo = ?, descripcion = ?, proceso_id = ?, estado = ? WHERE id = ?',
      [titulo, descripcion, proceso_id, estado, id]
    );

    res.status(200).json({ success: true, message: 'Registro actualizado correctamente.' });
  } catch (error) {
    console.error('Error al actualizar registro:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// DELETE /api/registros/:id
const eliminar = async (req, res) => {
  try {
    const { id } = req.params;

    const [existe] = await db.execute('SELECT id FROM registros WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Registro no encontrado.' });
    }

    await db.execute('DELETE FROM registros WHERE id = ?', [id]);
    res.status(200).json({ success: true, message: 'Registro eliminado correctamente.' });
  } catch (error) {
    console.error('Error al eliminar registro:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

module.exports = { listar, obtenerPorId, crear, actualizar, eliminar };
