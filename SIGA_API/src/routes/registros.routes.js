// src/routes/registros.routes.js
const express = require('express');
const router = express.Router();
const { listar, obtenerPorId, crear, actualizar, eliminar } = require('../controllers/registros.controller');
const { verificarToken, verificarRol } = require('../middlewares/auth.middleware');

router.use(verificarToken);

router.get('/', listar);
router.get('/:id', obtenerPorId);
router.post('/', crear);
router.put('/:id', actualizar);
router.delete('/:id', verificarRol('admin', 'supervisor'), eliminar);

module.exports = router;
