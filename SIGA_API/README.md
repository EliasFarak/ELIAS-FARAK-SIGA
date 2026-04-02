# SIGA API — Documentación de Servicios Web
**GA7-220501096-AA5-EV03 | Diseño y Desarrollo de Servicios Web**

> **Autor:** Elías José Farak Arévalo  
> **Programa:** Tecnología en Análisis y Desarrollo de Software — SENA  
> **Sistema:** SIGA — Sistema de Gestión Integral  
> **Versión:** 1.0.0

---

## Descripción General

La **API REST de SIGA** es el conjunto de servicios web diseñados para gestionar la capa lógica del Sistema de Gestión Integral. Está construida con **Node.js + Express** y se conecta a una base de datos **MySQL**. Implementa autenticación mediante **JWT (JSON Web Tokens)** y control de acceso por roles.

### Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| Node.js | 18+ | Entorno de ejecución |
| Express | 4.18 | Framework web |
| MySQL2 | 3.6 | Conexión a base de datos |
| JWT (jsonwebtoken) | 9.0 | Autenticación |
| bcryptjs | 2.4 | Cifrado de contraseñas |
| dotenv | 16.3 | Variables de entorno |
| morgan | 1.10 | Logging de peticiones |
| cors | 2.8 | Política de origen cruzado |

---

## Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/EliasFarak/SIGA-API.git
cd SIGA-API
```

### 2. Instalar dependencias
```bash
npm install
```

### 3. Configurar variables de entorno
```bash
cp .env.example .env
```
Editar el archivo `.env` con los datos de tu entorno:
```
PORT=3000
DB_HOST=localhost
DB_PORT=3306
DB_USER=root
DB_PASSWORD=tu_password
DB_NAME=siga_db
JWT_SECRET=siga_secret_key_2024
JWT_EXPIRES_IN=24h
```

### 4. Crear la base de datos
```bash
mysql -u root -p < docs/siga_db.sql
```

### 5. Iniciar el servidor
```bash
# Desarrollo (con recarga automática)
npm run dev

# Producción
npm start
```

El servidor quedará disponible en: `http://localhost:3000`

---

## Estructura del Proyecto

```
SIGA_API/
├── src/
│   ├── index.js                  # Punto de entrada principal
│   ├── config/
│   │   └── database.js           # Configuración de conexión MySQL
│   ├── controllers/
│   │   ├── auth.controller.js    # Login, registro, perfil
│   │   ├── usuarios.controller.js
│   │   ├── procesos.controller.js
│   │   ├── registros.controller.js
│   │   └── reportes.controller.js
│   ├── routes/
│   │   ├── auth.routes.js
│   │   ├── usuarios.routes.js
│   │   ├── procesos.routes.js
│   │   ├── registros.routes.js
│   │   └── reportes.routes.js
│   └── middlewares/
│       └── auth.middleware.js    # Verificación JWT y roles
├── docs/
│   └── siga_db.sql               # Script SQL de la base de datos
├── .env.example
├── .gitignore
├── package.json
├── ENLACE_REPOSITORIO.txt
└── README.md
```

---

## Autenticación

Todos los endpoints (excepto `POST /api/auth/login` y `POST /api/auth/registro`) requieren un token JWT válido enviado en el encabezado HTTP:

```
Authorization: Bearer <token>
```

### Roles del sistema

| Rol | Descripción |
|---|---|
| `admin` | Acceso total a todos los módulos |
| `supervisor` | Crear/editar procesos y registros; ver reportes |
| `usuario` | Crear y consultar registros propios |

---

## Documentación de Servicios (Endpoints)

---

### Módulo 1 — Autenticación (`/api/auth`)

---

#### `POST /api/auth/login`
Autentica a un usuario en el sistema y devuelve un token JWT.

**Acceso:** Público

**Body (JSON):**
```json
{
  "correo": "admin@siga.com",
  "contrasena": "Admin123*"
}
```

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "message": "Inicio de sesión exitoso.",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "usuario": {
      "id": 1,
      "nombre": "Administrador SIGA",
      "correo": "admin@siga.com",
      "rol": "admin"
    }
  }
}
```

**Errores posibles:**

| Código | Causa |
|---|---|
| `400` | Faltan campos requeridos |
| `401` | Credenciales incorrectas |
| `500` | Error interno del servidor |

---

#### `POST /api/auth/registro`
Registra un nuevo usuario en el sistema.

**Acceso:** Público

**Body (JSON):**
```json
{
  "nombre": "Juan Pérez",
  "correo": "juan@empresa.com",
  "contrasena": "MiClave123*",
  "rol": "usuario"
}
```

**Respuesta exitosa `201`:**
```json
{
  "success": true,
  "message": "Usuario registrado exitosamente.",
  "data": {
    "id": 4,
    "nombre": "Juan Pérez",
    "correo": "juan@empresa.com",
    "rol": "usuario"
  }
}
```

**Errores posibles:**

| Código | Causa |
|---|---|
| `400` | Campos requeridos faltantes |
| `409` | El correo ya está registrado |
| `500` | Error interno |

---

#### `GET /api/auth/perfil`
Obtiene el perfil del usuario autenticado.

**Acceso:** Requiere token JWT

**Headers:**
```
Authorization: Bearer <token>
```

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "nombre": "Administrador SIGA",
    "correo": "admin@siga.com",
    "rol": "admin",
    "estado": "activo",
    "fecha_creacion": "2024-01-15T10:30:00.000Z"
  }
}
```

---

### Módulo 2 — Usuarios (`/api/usuarios`)

> Todos los endpoints requieren token JWT y rol `admin`.

---

#### `GET /api/usuarios`
Lista todos los usuarios registrados en el sistema.

**Acceso:** admin

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "total": 3,
  "data": [
    {
      "id": 1,
      "nombre": "Administrador SIGA",
      "correo": "admin@siga.com",
      "rol": "admin",
      "estado": "activo",
      "fecha_creacion": "2024-01-15T10:30:00.000Z"
    }
  ]
}
```

---

#### `GET /api/usuarios/:id`
Obtiene la información de un usuario por su ID.

**Acceso:** admin

**Parámetro de ruta:** `id` — identificador numérico del usuario

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "data": {
    "id": 2,
    "nombre": "Supervisor General",
    "correo": "supervisor@siga.com",
    "rol": "supervisor",
    "estado": "activo"
  }
}
```

**Errores posibles:**

| Código | Causa |
|---|---|
| `404` | Usuario no encontrado |

---

#### `PUT /api/usuarios/:id`
Actualiza los datos de un usuario existente.

**Acceso:** admin

**Body (JSON):**
```json
{
  "nombre": "Supervisor Actualizado",
  "correo": "supervisor.nuevo@siga.com",
  "rol": "supervisor",
  "estado": "activo"
}
```

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "message": "Usuario actualizado correctamente."
}
```

---

#### `DELETE /api/usuarios/:id`
Desactiva un usuario del sistema (eliminación lógica).

**Acceso:** admin

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "message": "Usuario desactivado correctamente."
}
```

---

### Módulo 3 — Procesos (`/api/procesos`)

---

#### `GET /api/procesos`
Lista todos los procesos administrativos registrados.

**Acceso:** Todos los roles autenticados

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "total": 3,
  "data": [
    {
      "id": 1,
      "nombre": "Gestión de Nómina",
      "descripcion": "Proceso encargado del control y pago de nómina.",
      "responsable_id": 1,
      "responsable_nombre": "Administrador SIGA",
      "estado": "activo",
      "fecha_creacion": "2024-01-15T10:00:00.000Z"
    }
  ]
}
```

---

#### `GET /api/procesos/:id`
Obtiene un proceso por su ID.

**Acceso:** Todos los roles autenticados

---

#### `POST /api/procesos`
Crea un nuevo proceso administrativo.

**Acceso:** admin, supervisor

**Body (JSON):**
```json
{
  "nombre": "Gestión de Contratos",
  "descripcion": "Administración y seguimiento de contratos empresariales.",
  "responsable_id": 2,
  "estado": "activo"
}
```

**Respuesta exitosa `201`:**
```json
{
  "success": true,
  "message": "Proceso creado exitosamente.",
  "data": {
    "id": 4,
    "nombre": "Gestión de Contratos",
    "descripcion": "Administración y seguimiento de contratos empresariales.",
    "responsable_id": 2,
    "estado": "activo"
  }
}
```

---

#### `PUT /api/procesos/:id`
Actualiza un proceso existente.

**Acceso:** admin, supervisor

**Body (JSON):**
```json
{
  "nombre": "Gestión de Contratos v2",
  "descripcion": "Descripción actualizada.",
  "responsable_id": 1,
  "estado": "activo"
}
```

---

#### `DELETE /api/procesos/:id`
Desactiva un proceso del sistema.

**Acceso:** admin

---

### Módulo 4 — Registros Operativos (`/api/registros`)

---

#### `GET /api/registros`
Lista los registros con soporte de filtros opcionales por query string.

**Acceso:** Todos los roles autenticados

**Query params opcionales:**

| Parámetro | Tipo | Descripción |
|---|---|---|
| `proceso_id` | number | Filtra por proceso |
| `estado` | string | `pendiente`, `en_proceso`, `completado`, `cancelado` |

**Ejemplo:** `GET /api/registros?proceso_id=1&estado=pendiente`

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "total": 1,
  "data": [
    {
      "id": 3,
      "titulo": "Solicitud cliente #001",
      "descripcion": "Solicitud de soporte técnico.",
      "proceso_id": 3,
      "proceso_nombre": "Atención al Cliente",
      "usuario_id": 3,
      "usuario_nombre": "Usuario Prueba",
      "estado": "pendiente",
      "fecha_creacion": "2024-01-15T11:00:00.000Z"
    }
  ]
}
```

---

#### `GET /api/registros/:id`
Obtiene un registro por su ID.

**Acceso:** Todos los roles autenticados

---

#### `POST /api/registros`
Crea un nuevo registro operativo. El `usuario_id` se toma automáticamente del token JWT.

**Acceso:** Todos los roles autenticados

**Body (JSON):**
```json
{
  "titulo": "Revisión mensual de inventario",
  "descripcion": "Conteo físico de existencias en bodega principal.",
  "proceso_id": 2,
  "estado": "pendiente"
}
```

**Respuesta exitosa `201`:**
```json
{
  "success": true,
  "message": "Registro creado exitosamente.",
  "data": {
    "id": 4,
    "titulo": "Revisión mensual de inventario",
    "descripcion": "Conteo físico de existencias en bodega principal.",
    "proceso_id": 2,
    "usuario_id": 3,
    "estado": "pendiente"
  }
}
```

---

#### `PUT /api/registros/:id`
Actualiza un registro existente.

**Acceso:** Todos los roles autenticados

**Body (JSON):**
```json
{
  "titulo": "Revisión mensual de inventario",
  "descripcion": "Actualizado: conteo completado con novedades.",
  "proceso_id": 2,
  "estado": "completado"
}
```

---

#### `DELETE /api/registros/:id`
Elimina permanentemente un registro.

**Acceso:** admin, supervisor

---

### Módulo 5 — Reportes (`/api/reportes`)

> Todos los endpoints requieren token JWT y rol `admin` o `supervisor`.

---

#### `GET /api/reportes/resumen-general`
Devuelve un resumen estadístico general del sistema.

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "data": {
    "totalUsuarios": 3,
    "totalProcesos": 3,
    "procesosActivos": 3,
    "procesosInactivos": 0,
    "totalRegistros": 3
  }
}
```

---

#### `GET /api/reportes/usuarios-por-rol`
Muestra la cantidad de usuarios agrupada por rol.

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "data": [
    { "rol": "admin", "cantidad": 1 },
    { "rol": "supervisor", "cantidad": 1 },
    { "rol": "usuario", "cantidad": 1 }
  ]
}
```

---

#### `GET /api/reportes/procesos-por-estado`
Muestra la cantidad de procesos agrupada por estado.

**Respuesta exitosa `200`:**
```json
{
  "success": true,
  "data": [
    { "estado": "activo", "cantidad": 3 },
    { "estado": "inactivo", "cantidad": 0 }
  ]
}
```

---

## Resumen de Endpoints

| Método | Endpoint | Descripción | Roles |
|---|---|---|---|
| POST | /api/auth/login | Iniciar sesión | Público |
| POST | /api/auth/registro | Registrar usuario | Público |
| GET | /api/auth/perfil | Ver perfil propio | Autenticado |
| GET | /api/usuarios | Listar usuarios | admin |
| GET | /api/usuarios/:id | Ver usuario | admin |
| PUT | /api/usuarios/:id | Actualizar usuario | admin |
| DELETE | /api/usuarios/:id | Desactivar usuario | admin |
| GET | /api/procesos | Listar procesos | Autenticado |
| GET | /api/procesos/:id | Ver proceso | Autenticado |
| POST | /api/procesos | Crear proceso | admin, supervisor |
| PUT | /api/procesos/:id | Actualizar proceso | admin, supervisor |
| DELETE | /api/procesos/:id | Eliminar proceso | admin |
| GET | /api/registros | Listar registros | Autenticado |
| GET | /api/registros/:id | Ver registro | Autenticado |
| POST | /api/registros | Crear registro | Autenticado |
| PUT | /api/registros/:id | Actualizar registro | Autenticado |
| DELETE | /api/registros/:id | Eliminar registro | admin, supervisor |
| GET | /api/reportes/resumen-general | Resumen general | admin, supervisor |
| GET | /api/reportes/usuarios-por-rol | Usuarios por rol | admin, supervisor |
| GET | /api/reportes/procesos-por-estado | Procesos por estado | admin, supervisor |
---

*Evidencia GA7-220501096-AA5-EV03 — Proyecto Formativo SENA*
