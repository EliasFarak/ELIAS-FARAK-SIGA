// src/controllers/auth.controller.js
// Controlador de autenticación para SIGA

const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('../config/database');
require('dotenv').config();

// POST /api/auth/login
const login = async (req, res) => {
  try {
    const { correo, contrasena } = req.body;

    if (!correo || !contrasena) {
      return res.status(400).json({
        success: false,
        message: 'Correo y contraseña son requeridos.'
      });
    }

    // Buscar usuario en la BD
    const [rows] = await db.execute(
      'SELECT * FROM usuarios WHERE correo = ? AND estado = "activo"',
      [correo]
    );

    if (rows.length === 0) {
      return res.status(401).json({
        success: false,
        message: 'Credenciales incorrectas.'
      });
    }

    const usuario = rows[0];
    const contrasenaValida = await bcrypt.compare(contrasena, usuario.contrasena);

    if (!contrasenaValida) {
      return res.status(401).json({
        success: false,
        message: 'Credenciales incorrectas.'
      });
    }

    // Generar token JWT
    const token = jwt.sign(
      {
        id: usuario.id,
        correo: usuario.correo,
        nombre: usuario.nombre,
        rol: usuario.rol
      },
      process.env.JWT_SECRET || 'siga_secret_key',
      { expiresIn: process.env.JWT_EXPIRES_IN || '24h' }
    );

    res.status(200).json({
      success: true,
      message: 'Inicio de sesión exitoso.',
      data: {
        token,
        usuario: {
          id: usuario.id,
          nombre: usuario.nombre,
          correo: usuario.correo,
          rol: usuario.rol
        }
      }
    });
  } catch (error) {
    console.error('Error en login:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// POST /api/auth/registro
const registro = async (req, res) => {
  try {
    const { nombre, correo, contrasena, rol = 'usuario' } = req.body;

    if (!nombre || !correo || !contrasena) {
      return res.status(400).json({
        success: false,
        message: 'Nombre, correo y contraseña son requeridos.'
      });
    }

    // Verificar si el correo ya existe
    const [existe] = await db.execute('SELECT id FROM usuarios WHERE correo = ?', [correo]);
    if (existe.length > 0) {
      return res.status(409).json({
        success: false,
        message: 'Ya existe un usuario registrado con ese correo.'
      });
    }

    const salt = await bcrypt.genSalt(10);
    const contrasenaHash = await bcrypt.hash(contrasena, salt);

    const [result] = await db.execute(
      'INSERT INTO usuarios (nombre, correo, contrasena, rol, estado) VALUES (?, ?, ?, ?, "activo")',
      [nombre, correo, contrasenaHash, rol]
    );

    res.status(201).json({
      success: true,
      message: 'Usuario registrado exitosamente.',
      data: { id: result.insertId, nombre, correo, rol }
    });
  } catch (error) {
    console.error('Error en registro:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

// GET /api/auth/perfil
const perfil = async (req, res) => {
  try {
    const [rows] = await db.execute(
      'SELECT id, nombre, correo, rol, estado, fecha_creacion FROM usuarios WHERE id = ?',
      [req.usuario.id]
    );

    if (rows.length === 0) {
      return res.status(404).json({ success: false, message: 'Usuario no encontrado.' });
    }

    res.status(200).json({ success: true, data: rows[0] });
  } catch (error) {
    console.error('Error al obtener perfil:', error);
    res.status(500).json({ success: false, message: 'Error interno del servidor.' });
  }
};

module.exports = { login, registro, perfil };
