// src/routes/usuarios.routes.js
const express = require('express');
const router = express.Router();
const { listar, obtenerPorId, actualizar, eliminar } = require('../controllers/usuarios.controller');
const { verificarToken, verificarRol } = require('../middlewares/auth.middleware');

router.use(verificarToken);

router.get('/', verificarRol('admin'), listar);
router.get('/:id', verificarRol('admin'), obtenerPorId);
router.put('/:id', verificarRol('admin'), actualizar);
router.delete('/:id', verificarRol('admin'), eliminar);

module.exports = router;
