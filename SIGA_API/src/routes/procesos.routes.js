// src/routes/procesos.routes.js
const express = require('express');
const router = express.Router();
const { listar, obtenerPorId, crear, actualizar, eliminar } = require('../controllers/procesos.controller');
const { verificarToken, verificarRol } = require('../middlewares/auth.middleware');

router.use(verificarToken);

router.get('/', listar);
router.get('/:id', obtenerPorId);
router.post('/', verificarRol('admin', 'supervisor'), crear);
router.put('/:id', verificarRol('admin', 'supervisor'), actualizar);
router.delete('/:id', verificarRol('admin'), eliminar);

module.exports = router;
