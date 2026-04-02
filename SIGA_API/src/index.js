// src/index.js
// Punto de entrada principal de la API REST - Sistema SIGA
// Proyecto Formativo SENA - Tecnología en Análisis y Desarrollo de Software

const express = require('express');
const cors = require('cors');
const morgan = require('morgan');
require('dotenv').config();

// Importar rutas
const authRoutes      = require('./routes/auth.routes');
const usuariosRoutes  = require('./routes/usuarios.routes');
const procesosRoutes  = require('./routes/procesos.routes');
const registrosRoutes = require('./routes/registros.routes');
const reportesRoutes  = require('./routes/reportes.routes');

const app = express();
const PORT = process.env.PORT || 3000;

// ─── Middlewares globales ────────────────────────────────────────────────────
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

// ─── Ruta raíz (health-check) ────────────────────────────────────────────────
app.get('/', (req, res) => {
  res.status(200).json({
    success: true,
    message: 'API REST SIGA - Sistema de Gestión Integral',
    version: '1.0.0',
    autor: 'Elías José Farak Arévalo',
    programa: 'Tecnología en Análisis y Desarrollo de Software - SENA',
    endpoints: {
      auth:      '/api/auth',
      usuarios:  '/api/usuarios',
      procesos:  '/api/procesos',
      registros: '/api/registros',
      reportes:  '/api/reportes'
    }
  });
});

// ─── Registrar rutas ─────────────────────────────────────────────────────────
app.use('/api/auth',      authRoutes);
app.use('/api/usuarios',  usuariosRoutes);
app.use('/api/procesos',  procesosRoutes);
app.use('/api/registros', registrosRoutes);
app.use('/api/reportes',  reportesRoutes);

// ─── Manejo de rutas no encontradas ─────────────────────────────────────────
app.use((req, res) => {
  res.status(404).json({
    success: false,
    message: `Ruta no encontrada: ${req.method} ${req.originalUrl}`
  });
});

// ─── Manejo global de errores ────────────────────────────────────────────────
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ success: false, message: 'Error interno del servidor.' });
});

// ─── Iniciar servidor ────────────────────────────────────────────────────────
app.listen(PORT, () => {
  console.log('═══════════════════════════════════════════');
  console.log('  API REST SIGA - Sistema de Gestión Integral');
  console.log(`  Servidor corriendo en: http://localhost:${PORT}`);
  console.log(`  Entorno: ${process.env.NODE_ENV || 'development'}`);
  console.log('═══════════════════════════════════════════');
});

module.exports = app;
