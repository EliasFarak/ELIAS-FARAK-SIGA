// src/middlewares/auth.middleware.js
// Middleware de autenticación JWT para SIGA

const jwt = require('jsonwebtoken');
require('dotenv').config();

const verificarToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1]; // Bearer TOKEN

  if (!token) {
    return res.status(401).json({
      success: false,
      message: 'Acceso denegado. Token no proporcionado.'
    });
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET || 'siga_secret_key');
    req.usuario = decoded;
    next();
  } catch (error) {
    return res.status(403).json({
      success: false,
      message: 'Token inválido o expirado.'
    });
  }
};

const verificarRol = (...rolesPermitidos) => {
  return (req, res, next) => {
    if (!req.usuario) {
      return res.status(401).json({ success: false, message: 'No autenticado.' });
    }
    if (!rolesPermitidos.includes(req.usuario.rol)) {
      return res.status(403).json({
        success: false,
        message: `Acceso denegado. Se requiere uno de los roles: ${rolesPermitidos.join(', ')}`
      });
    }
    next();
  };
};

module.exports = { verificarToken, verificarRol };
