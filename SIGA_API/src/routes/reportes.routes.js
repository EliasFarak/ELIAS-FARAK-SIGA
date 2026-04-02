// src/routes/reportes.routes.js
const express = require('express');
const router = express.Router();
const { resumenGeneral, usuariosPorRol, procesosPorEstado } = require('../controllers/reportes.controller');
const { verificarToken, verificarRol } = require('../middlewares/auth.middleware');

router.use(verificarToken);
router.use(verificarRol('admin', 'supervisor'));

router.get('/resumen-general', resumenGeneral);
router.get('/usuarios-por-rol', usuariosPorRol);
router.get('/procesos-por-estado', procesosPorEstado);

module.exports = router;
