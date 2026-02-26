# SIGA - Módulo de Gestión de Usuarios

## GA7-220501096-AA2-EV01 — Codificación de módulos del software

---

## Información del proyecto

| Campo            | Detalle                                      |
|------------------|----------------------------------------------|
| **Sistema**      | SIGA - Sistema Integrado de Gestión Administrativa |
| **Módulo**       | Gestión de Usuarios                          |
| **Evidencia**    | GA7-220501096-AA2-EV01                       |
| **Autor**        | Elías José Farak Arévalo                     |
| **Programa**     | Tecnólogo en Análisis y Desarrollo de Software |
| **Institución**  | SENA - Centro de Servicios Financieros       |
| **Ficha**        | 3070323                                      |
| **Instructor**   | Francisco Arnaldo Vargas Bermúdez            |
| **Año**          | 2026                                         |

---

## Requisitos previos

- Java JDK 11 o superior
- MySQL Server 8.0 o superior
- Driver JDBC MySQL Connector/J (colocar el `.jar` en la carpeta `lib/`)
- VS Code con extensión "Extension Pack for Java"

---

## Estructura del proyecto

```
SIGA_Usuarios/
├── src/
│   └── com/
│       └── siga/
│           ├── modulo/
│           │   └── usuarios/
│           │       └── Main.java           ← Punto de entrada
│           ├── conexion/
│           │   └── ConexionDB.java         ← Conexión JDBC (Singleton)
│           ├── modelo/
│           │   └── Usuario.java            ← Entidad de dominio
│           ├── dao/
│           │   ├── IUsuarioDao.java        ← Interfaz CRUD
│           │   └── UsuarioDao.java         ← Implementación CRUD
│           └── vista/
│               └── MenuUsuarios.java       ← Menú de consola
├── lib/
│   └── mysql-connector-j-8.x.x.jar        ← Driver JDBC (descargar aparte)
├── siga_db.sql                             ← Script de base de datos
└── README.md
```

---

## Pasos para ejecutar el proyecto

### 1. Preparar la base de datos

Abre MySQL Workbench o la terminal MySQL y ejecuta:

```sql
source ruta/SIGA_Usuarios/siga_db.sql
```

### 2. Descargar el Driver JDBC

Descarga `mysql-connector-j` desde:  
https://dev.mysql.com/downloads/connector/j/

Copia el archivo `.jar` en la carpeta `lib/` del proyecto.

### 3. Verificar credenciales en ConexionDB.java

```java
private static final String URL_CONEXION   = "jdbc:mysql://localhost:3306/siga_db";
private static final String USUARIO_DB     = "root";       // Cambiar si aplica
private static final String CONTRASENA_DB  = "admin123";   // Cambiar si aplica
```

### 4. Configurar VS Code

Agrega el `.jar` del driver al classpath del proyecto en VS Code:
- Abre la paleta de comandos → `Java: Configure Classpath`
- Añade `lib/mysql-connector-j-X.X.X.jar`

### 5. Ejecutar

Abre `Main.java` y presiona **Run** o usa la terminal:

```bash
javac -cp lib/mysql-connector-j-8.x.x.jar -d bin src/com/siga/**/*.java src/com/siga/modulo/usuarios/*.java
java -cp bin:lib/mysql-connector-j-8.x.x.jar com.siga.modulo.usuarios.Main
```

---

## Funcionalidades del módulo

| Opción | Operación     | Descripción                                 |
|--------|---------------|---------------------------------------------|
| 1      | **Insertar**  | Registra un nuevo usuario en el sistema     |
| 2      | **Consultar** | Lista todos los usuarios registrados        |
| 3      | **Buscar**    | Consulta un usuario específico por su ID    |
| 4      | **Actualizar**| Modifica los datos de un usuario existente  |
| 5      | **Eliminar**  | Elimina un usuario tras confirmación        |
| 6      | **Salir**     | Cierra el módulo                            |

---

## Estándares de codificación aplicados

- **Paquetes**: minúsculas (`com.siga.modulo.usuarios`)
- **Clases**: PascalCase (`UsuarioDao`, `MenuUsuarios`, `ConexionDB`)
- **Variables y métodos**: camelCase (`idUsuario`, `insertarUsuario()`)
- **Constantes**: MAYÚSCULAS_CON_GUION (`SQL_INSERTAR`, `OPCION_SALIR`)
- **Comentarios**: Javadoc en todas las clases y métodos públicos
- **Patrón**: DAO (Data Access Object) + Singleton para conexión

---

## Repositorio en GitHub

Enlace al repositorio del proyecto:  
🔗 https://github.com/[tu-usuario]/SIGA_Usuarios

---

*Proyecto formativo - SENA 2026*
