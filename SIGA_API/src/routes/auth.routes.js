// src/routes/auth.routes.js
const express = require('express');
const router = express.Router();
const { login, registro, perfil } = require('../controllers/auth.controller');
const { verificarToken } = require('../middlewares/auth.middleware');

router.post('/login', login);
router.post('/registro', registro);
router.get('/perfil', verificarToken, perfil);

module.exports = router;
