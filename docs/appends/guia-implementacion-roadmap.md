# Guía: qué se hizo en el proyecto CompassMap y por qué

> Documento pensado para alguien **sin conocimientos previos de Java**.
> Objetivo: que entiendas (y puedas explicar) qué cambio se hizo, por qué se hizo
> así, y cómo comprobarlo tú mismo.

---

## 1. Qué es este proyecto, en una frase

`CompassMap` es una aplicación hecha con **Spring Boot** (una herramienta para
crear aplicaciones web en Java). Su idea de negocio es:

> Ayudar a una persona a saber **qué perfil de programador tiene** (principiante,
> desarrollador experimentado, etc.) respondiendo un cuestionario, y luego
> mostrarle una **hoja de ruta de aprendizaje** ("roadmap") adaptada a ese perfil.

En esta versión **solo hay backend** (el "motor" por detrás, sin pantallas
bonitas): datos, lógica y unos servicios web (REST) que devuelven información.

La aplicación ya tenía una parte hecha: la de **Clientes** (`Customer`). El
trabajo de esta sesión fue **crear la parte de "Roadmap"** (la hoja de ruta),
copiando el mismo estilo que ya usaba la parte de Clientes.

---

## 2. La idea clave: qué es un "dominio" y por qué se copió el patrón

En este proyecto, cada concepto de negocio (Cliente, Roadmap, Perfil...) se llama
un **dominio**. Cada dominio se construye siempre con las **mismas 4 piezas**.
Piensa en una biblioteca:

| Pieza | Nombre técnico | Analogía | Para qué sirve |
|-------|----------------|----------|----------------|
| 1 | **Model / Entity** (`@Entity`) | El **formulario** o la ficha | Define *qué datos* tiene un roadmap: nombre, descripción, nº de pasos, si está completado |
| 2 | **Repository** | El **bibliotecario** | Guarda y recupera fichas en la base de datos (crear, buscar, borrar) |
| 3 | **Service** | El **encargado** | Contiene las reglas y coordina al bibliotecario (por ejemplo, asignar un identificador único) |
| 4 | **Controller (REST)** | La **recepción** | Atiende peticiones web y responde en formato JSON (para apps o Postman) |

Además, en este proyecto hay dos piezas de apoyo:

| Pieza | Nombre técnico | Analogía | Para qué sirve |
|-------|----------------|----------|----------------|
| 5 | **Populator** | El **generador de ejemplos** | Rellena la base de datos con datos de prueba (roadmaps de ejemplo) |
| 6 | **BackOffice (menú)** | El **menú del cajero** | Un menú de texto (consola) para probar todo manualmente |

**Por qué se copió el patrón:** porque la parte de `Customer` ya funcionaba. Si el
dominio nuevo se construye igual, el proyecto queda **coherente** y es fácil de
leer: "lo que aprendas de un dominio, vale para todos".

---

## 3. Archivos que se CREARON (nuevos)

Ruta base: `compassMap/src/main/java/com/example/demo/`

### 3.1 `repository/RoadMapRepository.java`
El **bibliotecario**. Es una línea de código que dice: "el encargado de guardar
RoadMaps en la base de datos es genérico". Spring genera el código real solo.
- Hereda de `CrudRepository<RoadMap, String>`: regala gratis las operaciones
  básicas (guardar, buscar por id, listar, contar, borrar).

### 3.2 `service/RoadMapService.java`
El **encargado**. Aquí está la lógica:
- `createRoadMap`: guarda el roadmap. **No** pone el id a mano: lo genera solo la
  anotación `@GeneratedValue(GenerationType.UUID)` de la entidad. (Ponerlo a mano
  provocaba un error, porque Hibernate intentaba "fusionar" una fila que no existía.)
- `getAllRoadMaps` / `getAllRoadMapsAsList`: devuelve todos.
- `getRoadMapById`, `deleteRoadMap`, `deleteAllRoadMaps`, `countRoadMaps`.
- `updateRoadMap`: actualiza solo los campos que vengan informados (para no borrar
  datos por accidente).

### 3.3 `controller/RoadMapRestController.java`
La **recepción** (interfaz web REST). Escucha peticiones en la dirección
`/api/v1/roadmaps` y responde con datos (JSON). Sus "puertas":

| Método HTTP | Dirección | Acción |
|-------------|-----------|--------|
| POST | `/api/v1/roadmaps/create` | Crear un roadmap |
| POST | `/api/v1/roadmaps/createAll` | Crear varios |
| PUT  | `/api/v1/roadmaps/update` | Actualizar |
| GET  | `/api/v1/roadmaps/getAll` | Ver todos |
| GET  | `/api/v1/roadmaps/byId?id=...` | Ver uno |
| GET  | `/api/v1/roadmaps/count` | Contar |
| DELETE | `/api/v1/roadmaps/byId?id=...` | Borrar uno |
| DELETE | `/api/v1/roadmaps/all` | Borrar todos |

---

## 4. Archivos que se MODIFICARON (existían antes)

### 4.1 `model/RoadMap.java`
Ya existía (el **formulario** con sus campos). Se le hicieron dos ajustes:
- Se añadió un método `toString()`: sirve para que, cuando se imprima un roadmap
  por pantalla, se vea con texto legible en lugar de un código raro.
- Los campos `steps` y `completed` pasaron de tipos "primitivos" (`int`, `boolean`)
  a tipos "envoltorio" (`Integer`, `Boolean`). Motivo: así se puede enviar una
  **actualización parcial** por REST (por ejemplo, cambiar solo el nombre) sin que
  falle al faltar `steps` o `completed`. Con un primitivo, un campo ausente es
  "nulo" y el sistema lo rechaza con un error `400 Bad Request`.

### 4.2 `utils/PopulatorDB.java`
El **generador de ejemplos**. Se le añadieron dos métodos:
- `createRoadMaps()`: crea 4 roadmaps de ejemplo en memoria (DDD Mastery,
  AI Shipping, Code Foundations, Tech Literacy).
- `createAndSaveRoadMap()`: los crea **y los guarda** en la base de datos.

### 4.3 `utils/BackOffice.java`
El **menú de consola**. Antes, la opción "2. Roadmap" decía
*"not implemented yet"*. Ahora:
- La opción **2** del menú principal abre un **submenú de Roadmap**.
- El submenú permite: crear roadmaps de ejemplo, listarlos en una **tabla
  formateada**, ver solo los **pendientes**, buscar por id, borrar, borrar todos y
  contar.

---

## 5. Cómo compilar y ejecutar (paso a paso)

> Requisito: el proyecto pide **Java 21**. En este equipo ya está en
> `C:\Users\jrobl\.jdks\temurin-21.0.12.1`.

Abre una terminal en la carpeta del proyecto Maven:
`D:\entorno-java\proyectos\compassMap\compassMap`

**Paso 1 — Compilar** (traducir el código y comprobar que no hay errores):

```powershell
$env:JAVA_HOME="C:\Users\jrobl\.jdks\temurin-21.0.12.1"
.\mvnw.cmd compile
```

Si no muestra errores (o termina con `BUILD SUCCESS`), está correcto.

**Paso 2 — Arrancar la aplicación:**

```powershell
.\mvnw.cmd spring-boot:run
```

Al arrancar, **la aplicación abre un menú de texto en esa misma terminal**
(es el `BackOffice`). Verás algo como:

```
===== MAIN MENU =====
1. Customer
2. Roadmap
3. Profile
4. Quit
Select an option:
```

**Paso 3 — Probar el Roadmap:** escribe `2` + Enter. Verás el menú de Roadmap:

```
===== ROADMAP MENU =====
1. Create roadmaps
2. Get all roadmaps
3. Get pending roadmaps
4. Get roadmap by Id
5. Delete roadmap
6. Delete all roadmaps
7. Count roadmaps
8. Quit
```

Prueba recomendada en orden: `1` (crear ejemplos), `2` (ver tabla), `3` (solo
pendientes), `7` (contar), y `8` (volver). Para salir del programa entero: `4`.

> Nota: mientras la aplicación está encendida, también sirve la web en
> `http://localhost:8080` (por ejemplo, la consola de base de datos
> `http://localhost:8080/h2-console`).

---

## 6. Cómo probar la parte REST (sin menú)

Con la aplicación encendida, en **otra** terminal (o con Postman/navegador):

- Ver todos:
  `http://localhost:8080/api/v1/roadmaps/getAll`
- Contar:
  `http://localhost:8080/api/v1/roadmaps/count`
- Crear uno (ejemplo con PowerShell):

```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8080/api/v1/roadmaps/create" `
  -ContentType "application/json" `
  -Body '{"name":"Nuevo Roadmap","description":"Prueba","steps":3,"completed":false}'
```

---

## 7. Por qué falla `mvnw test` (explicación sencilla)

`mvnw test` ejecuta las **pruebas automáticas**. Fallan por un motivo que **ya
existía antes** de este trabajo, no por el código del Roadmap:

1. Al ejecutar las pruebas, Spring Boot levanta la aplicación entera.
2. Al levantarla, ejecuta un bloque de arranque (`CommandLineRunner`) que
   **abre el menú de consola** y se queda esperando a que alguien escriba.
3. En las pruebas automáticas **no hay nadie escribiendo**, así que el programa
   "se queda sin respuesta" y lanza el error
   `NoSuchElementException: No line found` (en `BackOffice.java`), tumbando la
   prueba.

La solución habitual es **impedir que el menú interactivo se ejecute durante los
tests** (por ejemplo, con un "perfil" `test`). Eso es un cambio aparte que se
puede hacer cuando quieras.

---

## 8. Cómo explicárselo al profesor (resumen en 5 frases)

1. I added a new **domain** called `RoadMap` to the CompassMap Spring Boot backend.
2. I followed the **same layered pattern** already used for `Customer`:
   `Entity` (data) → `Repository` (persistence) → `Service` (logic) →
   `RestController` (HTTP/JSON API).
3. I exposed it through **REST endpoints** under `/api/v1/roadmaps`
   (create, read, update, delete, count).
4. I wired it into the **console `BackOffice`** with a new submenu and a
   **stream-based formatted table** to create and list sample roadmaps.
5. Compilation passes; the existing `mvnw test` failure is **pre-existing** and
   caused by the interactive `CommandLineRunner` reading `System.in` with no
   input available in the test environment.

---

## 9. Mini-glosario

- **Java**: lenguaje de programación.
- **Spring Boot**: marco que facilita crear aplicaciones Java (web, bases de datos...).
- **Entidad / Model**: clase que representa "una cosa" con sus datos (aquí, un Roadmap).
- **Base de datos**: donde se guarda la información (aquí, H2).
- **Repository**: pieza que habla con la base de datos.
- **Service**: pieza con la lógica de negocio.
- **Controller / REST**: pieza que atiende peticiones por internet y responde (JSON).
- **JSON**: formato de texto para intercambiar datos, tipo `{"name":"...","steps":3}`.
- **UUID**: código único e irrepetible que se usa como identificador.
- **Maven (`mvnw`)**: herramienta que compila, prueba y arranca el proyecto.
- **`mvnw compile`**: comprobar que el código compila.
- **`mvnw test`**: ejecutar las pruebas automáticas.
- **`mvnw spring-boot:run`**: arrancar la aplicación.
