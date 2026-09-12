<div align="center">

# 📚 SpringEduManager

### Aplicación web educativa desarrollada con el ecosistema Spring

*Proyecto de evaluación — Módulo 6: Desarrollo de aplicaciones JEE con Spring Framework*
*Bootcamp Java Full Stack — Alkemy / UNAB*

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.8-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![H2](https://img.shields.io/badge/H2%20Database-0078D4?style=for-the-badge&logo=databricks&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

</div>

---

## 📖 Descripción

**SpringEduManager** es una aplicación web interna pensada para la Coordinación Académica de un bootcamp. Permite que los estudiantes se registren, visualicen sus cursos, revisen sus prácticas y consulten sus evaluaciones, centralizando información que antes vivía dispersa en hojas de cálculo y formularios aislados.

El proyecto se construyó **progresivamente, en cinco etapas**, cada una correspondiente a una lección del módulo, integrando de forma continua Maven, Spring MVC, persistencia con JPA, seguridad con Spring Security y una API REST.

---

## 🧭 Tabla de contenidos

- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Modelo de datos](#-modelo-de-datos)
- [Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto)
- [Usuarios de prueba](#-usuarios-de-prueba)
- [Rutas web disponibles](#-rutas-web-disponibles)
- [API REST](#-api-rest)
- [Consola de la base de datos](#-consola-de-la-base-de-datos)
- [Progreso por etapas](#-progreso-por-etapas)
- [Autor](#-autor)

---

## 🛠️ Tecnologías utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 21 (LTS) |
| Framework | Spring Boot 4.0.8 |
| Gestor de dependencias | Maven |
| Arquitectura web | Spring MVC |
| Motor de plantillas | Thymeleaf |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos | H2 (en memoria) |
| Seguridad | Spring Security + BCrypt |
| API | REST con JSON |
| Control de versiones | Git + GitHub |
| IDE | IntelliJ IDEA |

---

## 📂 Estructura del proyecto

El código sigue una arquitectura por capas, separando responsabilidades:

```
src/main/java/cl/untec/springedumanager/
│
├── config/          → Configuración de seguridad (SecurityConfig)
├── controller/       → Controladores web (MVC) y REST
├── exception/        → Excepciones personalizadas
├── model/             → Entidades JPA (las tablas de la base de datos)
├── repository/        → Repositorios Spring Data JPA
├── service/            → Lógica de negocio (autenticación de usuarios)
└── SpringEduManagerApplication.java   → Clase principal

src/main/resources/
├── templates/        → Vistas HTML (Thymeleaf)
└── application.properties   → Configuración de la app y la base de datos
```

---

## 🗂️ Modelo de datos

El proyecto gestiona **6 entidades relacionadas**:

| Entidad | Descripción |
|---|---|
| `Course` | Un curso del bootcamp (nombre, código) |
| `Student` | Un estudiante registrado |
| `Evaluation` | La nota de un estudiante en un curso |
| `Practice` | Un trabajo práctico asociado a un curso |
| `Enrollment` | La inscripción que une a un estudiante con un curso |
| `User` | Las credenciales de acceso (login) de un estudiante |

**Relaciones:**

```
Course  ──────< Practice           (un curso tiene muchas prácticas)
Course  ──────< Enrollment >──────  Student   (la inscripción conecta a ambos)
Course  ──────< Evaluation >──────  Student   (la nota de un estudiante en un curso)
Student ────── User                (relación uno a uno, para el login)
```

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- Java 21 instalado
- IntelliJ IDEA (o cualquier IDE compatible con Maven)

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/jocofre03-lab/SpringEduManager.git
   ```
2. Ábrelo en IntelliJ IDEA como proyecto Maven.
3. Espera a que Maven descargue las dependencias.
4. Ejecuta la clase `SpringEduManagerApplication.java`.
5. La aplicación estará disponible en:
   ```
   http://localhost:8080
   ```

Al arrancar, la aplicación crea automáticamente datos de prueba (cursos y usuarios) si la base de datos está vacía.

---

## 👤 Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | `ADMIN` |
| `estudiante1` | `estudiante123` | `STUDENT` |

> ⚠️ Solo un usuario con rol **ADMIN** puede crear nuevos cursos (`/courses/new`). Las contraseñas se almacenan encriptadas con **BCrypt**, nunca en texto plano.

---

## 🌐 Rutas web disponibles

| Ruta | Método | Descripción | Acceso |
|---|---|---|---|
| `/home` | GET | Página de inicio | Público |
| `/courses` | GET | Lista de cursos (vista HTML) | Autenticado |
| `/courses/new` | POST | Crear un curso nuevo | Solo `ADMIN` |
| `/login` | GET | Formulario de inicio de sesión | Público |
| `/logout` | POST | Cerrar sesión | Autenticado |

---

## 🔌 API REST

Base URL: `/api/v1/courses`

| Verbo | Ruta | Descripción | Cuerpo (JSON) |
|---|---|---|---|
| `GET` | `/api/v1/courses` | Lista todos los cursos | — |
| `GET` | `/api/v1/courses/{id}` | Obtiene un curso por id | — |
| `POST` | `/api/v1/courses` | Crea un curso nuevo | `{"name": "...", "code": "..."}` |
| `PUT` | `/api/v1/courses/{id}` | Actualiza un curso existente | `{"name": "...", "code": "..."}` |
| `DELETE` | `/api/v1/courses/{id}` | Elimina un curso | — |

La API está protegida con Spring Security y admite tanto **login por formulario** como **Basic Auth**, ideal para ser consumida por otros sistemas o aplicaciones externas.

### Ejemplo con `curl`

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/v1/courses \
  -H "Content-Type: application/json" \
  -d '{"name":"Bases de Datos","code":"BD-001"}'
```

Si un recurso no existe, la API responde con un código **404** claro (`CourseNotFoundException`), en lugar de un error genérico.

---

## 🗄️ Consola de la base de datos

Con la aplicación en ejecución, se puede inspeccionar la base de datos H2 desde el navegador:

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:springedu` |
| Usuario | `sa` |
| Contraseña | *(vacía)* |

---

## ✅ Progreso por etapas

El proyecto se desarrolló siguiendo la evolución progresiva propuesta por la evaluación:

- [x] **Etapa 1 — El gestor de proyectos:** proyecto Spring Boot creado con Maven.
- [x] **Etapa 2 — Spring MVC:** controladores, vistas Thymeleaf y patrón MVC funcional.
- [x] **Etapa 3 — Acceso a datos:** entidades JPA, repositorios y relaciones sobre base de datos H2.
- [x] **Etapa 4 — Spring Security:** login/logout, roles, rutas protegidas y contraseñas con BCrypt.
- [x] **Etapa 5 — Interoperabilidad:** API REST con JSON, consumible por sistemas externos.

---

## 👩‍💻 Autor

**Fernanda Saavedra**
Estudiante — Bootcamp Java Full Stack.

<div align="center">

*Desarrollado como proyecto de evaluación del Módulo 6 — 2026*

</div>
