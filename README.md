<div align="center">

# 📚 SpringEduManager

### Aplicación web educativa desarrollada con el ecosistema Spring

*Proyecto de evaluación — Módulo 6: Desarrollo de aplicaciones JEE con Spring Framework*
*Bootcamp Java Full Stack — Alkemy / UNAB*

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.8-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

</div>

---

## 📖 Descripción

**SpringEduManager** es una aplicación web interna pensada para la Coordinación Académica de un bootcamp. Permite que los estudiantes se **registren** mediante un formulario web, **visualicen sus cursos**, **revisen sus prácticas** y **consulten sus evaluaciones**, centralizando información que antes vivía dispersa en hojas de cálculo y formularios aislados. Un usuario con rol **ADMIN** puede además crear nuevos cursos desde un formulario protegido.

El proyecto se construyó **progresivamente, en cinco etapas**, cada una correspondiente a una lección del módulo, integrando de forma continua Maven, Spring MVC, persistencia con JPA, seguridad con Spring Security y una API REST.

> 🔄 El proyecto se desarrolló inicialmente sobre **H2** (base de datos en memoria) y luego se migró a **MariaDB** como base de datos persistente en disco, para practicar la conexión a un motor externo real. Gracias a JPA, la migración solo requirió cambiar la configuración de conexión en `application.properties`: ninguna entidad, repositorio ni controlador tuvo que modificarse. **Para esta entrega, la configuración activa es H2**, de modo que el proyecto se ejecute de inmediato al clonarlo, sin depender de un servidor de base de datos externo instalado en la máquina de quien lo revise. La configuración de MariaDB queda comentada y documentada en la sección [Base de datos](#-base-de-datos) para quien quiera probarla.

---

## 🧭 Tabla de contenidos

- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Modelo de datos](#-modelo-de-datos)
- [Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto)
- [Usuarios de prueba](#-usuarios-de-prueba)
- [Rutas web disponibles](#-rutas-web-disponibles)
- [API REST](#-api-rest)
- [Base de datos](#-base-de-datos)
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
| Base de datos | H2 (en memoria, activa para la entrega) — también migrado y probado con MariaDB |
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
├── service/            → Lógica de negocio (Course, Student, Practice, Evaluation, autenticación)
└── SpringEduManagerApplication.java   → Clase principal

src/main/resources/
├── static/css/       → Hoja de estilos (style.css)
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

> ✅ **Inscripciones y visibilidad por rol:** un usuario **ADMIN** puede inscribir estudiantes en cursos desde `/enrollments/new`, usando la entidad `Enrollment`. A partir de esa inscripción, las vistas `/courses`, `/practices` y `/evaluations` se comportan según el rol de quien inició sesión: un **ADMIN** ve el listado completo del sistema, mientras que un **STUDENT** ve únicamente los cursos en los que está inscrito, las prácticas de esos cursos, y sus propias evaluaciones — nunca la información de otros estudiantes.

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- Java 21 instalado
- IntelliJ IDEA (o cualquier IDE compatible con Maven)
- No requiere instalar ninguna base de datos externa: la entrega usa **H2** en memoria

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

Al arrancar, la aplicación crea automáticamente datos de prueba (cursos y usuarios) si la base de datos está vacía. Las tablas se generan solas gracias a `spring.jpa.hibernate.ddl-auto=update`.

> 💡 El proyecto conserva, comentada dentro de `application.properties`, la configuración de conexión a **MariaDB** usada durante el desarrollo. Para probarla, se requiere tener MariaDB instalado y en ejecución (por ejemplo, vía Homebrew: `brew services start mariadb`), crear la base de datos y un usuario dedicado, y luego comentar el bloque de H2 y descomentar el de MariaDB — sin tocar ninguna otra parte del código:
> ```sql
> CREATE DATABASE springedu;
> CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'springpass123';
> GRANT ALL PRIVILEGES ON springedu.* TO 'springuser'@'localhost';
> FLUSH PRIVILEGES;
> ```

---

## 👤 Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | `ADMIN` |
| `estudiante1` | `estudiante123` | `STUDENT` |

> ⚠️ Solo un usuario con rol **ADMIN** puede crear nuevos cursos (`/courses/new`). Las contraseñas se almacenan encriptadas con **BCrypt**, nunca en texto plano.

> 🔐 Al registrarse desde `/students/new`, cada estudiante define su propio email (como nombre de usuario) y contraseña (mínimo 6 caracteres), guardada siempre encriptada con **BCrypt**. Se crea automáticamente un `User` con rol `STUDENT`, vinculado a su `Student` mediante la relación `@OneToOne`, permitiéndole iniciar sesión de inmediato para consultar sus cursos, prácticas y evaluaciones.

---

## 🌐 Rutas web disponibles

| Ruta | Método | Descripción | Acceso |
|---|---|---|---|
| `/home` | GET | Página de inicio | Público |
| `/students` | GET | Lista de estudiantes registrados | Autenticado |
| `/students/new` | GET | Formulario de registro de estudiantes | Autenticado |
| `/students/new` | POST | Procesa el registro (contraseña propia + valida email duplicado) | Autenticado |
| `/courses` | GET | Cursos: **todos** si es ADMIN, solo los inscritos si es STUDENT | Autenticado |
| `/courses/new` | GET | Formulario de creación de cursos | Solo `ADMIN` |
| `/courses/new` | POST | Procesa la creación de un curso | Solo `ADMIN` |
| `/enrollments` | GET | Lista de todas las inscripciones | Solo `ADMIN` |
| `/enrollments/new` | GET | Formulario para inscribir un estudiante en un curso | Solo `ADMIN` |
| `/enrollments/new` | POST | Procesa la inscripción | Solo `ADMIN` |
| `/practices` | GET | Prácticas: **todas** si es ADMIN, solo las de sus cursos si es STUDENT | Autenticado |
| `/evaluations` | GET | Evaluaciones: **todas** si es ADMIN, solo las propias si es STUDENT | Autenticado |
| `/login` | GET | Formulario de inicio de sesión | Público |
| `/logout` | POST | Cerrar sesión | Autenticado |

> 📝 Si un estudiante intenta registrarse con un email ya existente, el formulario muestra un mensaje de error y no crea un registro duplicado. Si un usuario sin rol `ADMIN` intenta acceder a `/courses/new` o `/enrollments/**`, Spring Security responde con **403 Forbidden**.

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

## 🗄️ Base de datos

**Para esta entrega**, la aplicación usa **H2** en memoria, para que cualquiera pueda clonar el repositorio y ejecutarlo sin instalar nada externo. Puede inspeccionarse desde el navegador, con la app en ejecución:

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:springedu` |
| Usuario | `sa` |
| Contraseña | *(vacía)* |

> ⚠️ Al ser una base en memoria, los datos se reinician cada vez que se detiene la aplicación.

### Alternativa: MariaDB (usada durante el desarrollo)

El proyecto también fue migrado y probado con **MariaDB** como base de datos persistente en disco. La configuración queda comentada en `application.properties`; para activarla, ver los pasos en [Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto).

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:mariadb://localhost:3306/springedu` |
| Usuario | `springuser` |
| Contraseña | `springpass123` |

Puede inspeccionarse directamente desde la terminal:

```bash
sudo mysql -u root
USE springedu;
SHOW TABLES;
SELECT * FROM course;
```

---

## ✅ Progreso por etapas

El proyecto se desarrolló siguiendo la evolución progresiva propuesta por la evaluación:

- [x] **Etapa 1 — El gestor de proyectos:** proyecto Spring Boot creado con Maven.
- [x] **Etapa 2 — Spring MVC:** controladores, vistas Thymeleaf y patrón MVC funcional.
- [x] **Etapa 3 — Acceso a datos:** entidades JPA, repositorios y relaciones, desarrolladas sobre H2.
- [x] **Etapa 4 — Spring Security:** login/logout, roles, rutas protegidas y contraseñas con BCrypt.
- [x] **Etapa 5 — Interoperabilidad:** API REST con JSON, consumible por sistemas externos.
- [x] **Migración de base de datos:** paso de H2 a MariaDB sin modificar entidades, repositorios ni controladores, evidenciando la independencia de motor que provee JPA.
- [x] **Arquitectura en capas:** Controller → Service → Repository en todas las entidades principales, separando la lógica de negocio del manejo de peticiones web.
- [x] **Registro con acceso automático:** cada estudiante registrado recibe credenciales de acceso vinculadas mediante la relación `User`–`Student` (`@OneToOne`).
- [x] **Identidad visual:** hoja de estilos propia con paleta "Astral Lagoon" y tipografías Playfair Display / Lato aplicada en todas las vistas.

---

## 👩‍💻 Autor

**Fernanda Saavedra**
Estudiante — Bootcamp Java Full Stack.

<div align="center">

*Desarrollado como proyecto de evaluación del Módulo 6 — 2026*

</div>
