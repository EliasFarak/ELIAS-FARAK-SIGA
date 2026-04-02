// src/controllers/usuarios.controller.js
// Controlador CRUD de Usuarios para SIGA

const bcrypt = require('bcryptjs');
const db = require('../config/database');

// GET /api/usuarios  — Listar todos los usuarios
const listar = async (req, res) => {
  try {
    const [rows] = await db.execute(
      'SELECT id, nombre, correo, rol, estado, fecha_creacion FROM usuarios ORDER BY nombre ASC'
    );
    res.status(200).json({ success: true, total: rows.length, data: rows });
  } catch (error) {
    console.error('Error al listar usuarios:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/usuarios/:id  — Obtener usuario por ID
const obtenerPorId = async (req, res) => {
  try {
    const { id } = req.params;
    const [rows] = await db.execute(
      'SELECT id, nombre, correo, rol, estado, fecha_creacion FROM usuarios WHERE id = ?',
      [id]
    );

    if (rows.length === 0) {
      return res.status(404).json({ success: false, message: 'Usuario no encontrado.' });
    }

    res.status(200).json({ success: true, data: rows[0] });
  } catch (error) {
    console.error('Error al obtener usuario:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// PUT /api/usuarios/:id  — Actualizar usuario
const actualizar = async (req, res) => {
  try {
    const { id } = req.params;
    const { nombre, correo, rol, estado } = req.body;

    const [existe] = await db.execute('SELECT id FROM usuarios WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Usuario no encontrado.' });
    }

    await db.execute(
      'UPDATE usuarios SET nombre = ?, correo = ?, rol = ?, estado = ? WHERE id = ?',
      [nombre, correo, rol, estado, id]
    );

    res.status(200).json({ success: true, message: 'Usuario actualizado correctamente.' });
  } catch (error) {
    console.error('Error al actualizar usuario:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// DELETE /api/usuarios/:id  — Eliminar usuario (lógico)
const eliminar = async (req, res) => {
  try {
    const { id } = req.params;

    const [existe] = await db.execute('SELECT id FROM usuarios WHERE id = ?', [id]);
    if (existe.length === 0) {
      return res.status(404).json({ success: false, message: 'Usuario no encontrado.' });
    }

    await db.execute('UPDATE usuarios SET estado = "inactivo" WHERE id = ?', [id]);

    res.status(200).json({ success: true, message: 'Usuario desactivado correctamente.' });
  } catch (error) {
    console.error('Error al eliminar usuario:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

module.exports = { listar, obtenerPorId, actualizar, eliminar };
