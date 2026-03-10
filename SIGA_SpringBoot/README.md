# SIGA - Módulo de Usuarios con Spring Boot

## GA7-220501096-AA3-EV01 — Codificación de módulos Stand alone, web y móvil

---

## Información del proyecto

| Campo           | Detalle                                             |
|-----------------|-----------------------------------------------------|
| **Sistema**     | SIGA - Sistema Integrado de Gestión Administrativa  |
| **Módulo**      | Gestión de Usuarios (Web)                           |
| **Framework**   | Spring Boot 3.2 + Thymeleaf + Spring Data JPA       |
| **Evidencia**   | GA7-220501096-AA3-EV01                              |
| **Autor**       | Elías José Farak Arévalo - GRUPO # 5                |
| **Programa**    | Tecnólogo en Análisis y Desarrollo de Software      |
| **Institución** | SENA - Centro de Servicios Financieros              |
| **Ficha**       | 3070323                                             |
| **Instructor**  | Francisco Arnaldo Vargas Bermúdez                   |
| **Año**         | 2026                                                |

---

## Requisitos previos

- Java JDK 17 o superior
- Maven 3.6 o superior
- MySQL Server 8.0 o superior
- VS Code con Extension Pack for Java

---

## Pasos para ejecutar

### 1. Preparar la base de datos
```sql
source ruta/SIGA_SpringBoot/siga_db.sql
```

### 2. Verificar credenciales en application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/siga_db
spring.datasource.username=root
spring.datasource.password=admin123
```

### 3. Ejecutar el proyecto
```bash
mvn spring-boot:run
```

### 4. Abrir en el navegador
```
http://localhost:8080/usuarios
```

---

## Funcionalidades

| Ruta                        | Método | Descripción              |
|-----------------------------|--------|--------------------------|
| `/usuarios`                 | GET    | Lista todos los usuarios |
| `/usuarios/nuevo`           | GET    | Formulario nuevo usuario |
| `/usuarios/guardar`         | POST   | Guarda nuevo usuario     |
| `/usuarios/editar/{id}`     | GET    | Formulario editar        |
| `/usuarios/actualizar`      | POST   | Actualiza usuario        |
| `/usuarios/eliminar/{id}`   | GET    | Elimina usuario          |

---

## Repositorio en GitHub
https://github.com/EliasFarak/ELIAS-FARAK-SIGA/tree/main/SIGA_SpringBoot
---

*Proyecto formativo - SENA 2026*
