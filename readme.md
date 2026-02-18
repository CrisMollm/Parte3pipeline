# DockerPipeLine

## Introducción

`DockerPipeLine` es una aplicación Spring Boot que gestiona usuarios, productos y pedidos. Utiliza Spring Data JPA para la persistencia y PostgreSQL como base de datos en producción; el proyecto está empaquetado con Maven y dispone de un `Dockerfile` y un `docker-compose.yml` para facilitar el despliegue.

La base de datos de prueba se inicializa a partir del script `src/main/resources/db/init.sql` (ver sección fixtures).

## Estructura del proyecto (resumen)

- `Dockerfile` — imagen de la aplicación
- `docker-compose.yml` — servicios (Postgres + app)
- `pom.xml` — build y dependencias Maven
- `src/main/java` — código fuente Java
- `src/main/resources` — configuración y `db/init.sql` (fixtures)
- `src/test/java` — tests JUnit
- `.github/workflows/ci.yml` — pipeline CI (GitHub Actions)

## Comandos para poner en marcha y cargar datos de prueba (fixtures)

Requisitos mínimos: Docker (para la opción Docker), Java 17+, Maven 3.6+ (si se ejecuta localmente con Maven).

1) Con Docker Compose (recomendado)

```bash
docker compose up --build
```

Este comando levanta un contenedor `postgres:15` y la `app`. El `docker-compose.yml` monta `./src/main/resources/db` en el directorio de inicialización de Postgres, por lo que el script `init.sql` se cargará automáticamente cuando el contenedor de la base de datos se inicie.

Acceda a la app en: http://localhost:8080

2) Ejecutar localmente con Maven (usando una base de datos PostgreSQL separada)

Construir:

```bash
mvn clean package
```

Ejecutar:

```bash
mvn spring-boot:run
# o
java -jar target/*.jar
```

Si la base de datos PostgreSQL no existe, créela (nombre por defecto en `application.properties`):

```
DB: dockerpipeline
USER: postgres
PASSWORD: postgres
```

Para importar manualmente los datos de prueba (si no usa `docker compose`):

```bash
# con psql instalado
psql -h localhost -U postgres -d dockerpipeline -f src/main/resources/db/init.sql
```

O bien, mediante un contenedor temporal de Postgres (útil si no tiene psql local):

```bash
docker run --rm -v %cd%/src/main/resources/db:/tmp -e PGPASSWORD=postgres postgres:15 \
  psql -h host.docker.internal -U postgres -d dockerpipeline -f /tmp/init.sql
```

Nota: en entornos Linux/Mac, sustituya `%cd%` por `$(pwd)` y `host.docker.internal` puede variar según la plataforma; cuando use `docker compose` no es necesaria esta importación manual porque el script ya está montado.

## Comandos para ejecutar los tests

- Ejecutar todos los tests con Maven:

```bash
mvn test
```

- Como la CI (build + tests):

```bash
mvn -B -DskipTests=false package
```

Los tests de integración usan una base H2 en memoria (ver `target/test-classes/application.properties`) y la anotación `@Sql("/db/init.sql")` para cargar datos de prueba en los tests de repositorio.

## Comprobación del funcionamiento (curl)

Ejemplos básicos para llamar los endpoints (GET):

```bash
curl -v http://localhost:8080/api/users
curl -v http://localhost:8080/api/products
curl -v http://localhost:8080/api/orders
```

Si prefiere una salida legible, puede canalizar la respuesta a `jq` (si lo tiene):

```bash
curl -s http://localhost:8080/api/users | jq '.'
```

Postman / colecciones: no hay ninguna exportación de Postman incluida en este repositorio. Si quiere, puedo generar una colección de ejemplo y añadirla al proyecto; indíqueme el formato que prefiera (Postman v2.1 / OpenAPI, etc.).

## Resumen y listado de los tests creados

Tipos de tests presentes en el proyecto:

- Tests de integración (y algunos tests tipo repositorio) con `@SpringBootTest` y `@Sql` para cargar fixtures.
- Tests de controller con `MockMvc` para verificar respuestas HTTP.

Tests principales (clases):

- `com.example.dockerpipeline.controller.ApiControllerTest` — comprueba que `/api/users`, `/api/products` y `/api/orders` devuelven `200 OK` (usa `@AutoConfigureMockMvc`).
- `com.example.dockerpipeline.repository.UserRepositoryTest` — tests CRUD y consultas sobre `UserRepository`, con `@Sql("/db/init.sql")` para datos iniciales.
- `com.example.dockerpipeline.repository.ProductRepositoryTest` — tests CRUD y consultas sobre `ProductRepository`.
- `com.example.dockerpipeline.repository.OrderRepositoryTest` — tests CRUD y consultas sobre `OrderRepository`.

Observaciones: los tests de repositorio cargan los datos de `src/main/resources/db/init.sql` antes de ejecutarse y utilizan una base H2 en memoria durante la ejecución de los tests.

## Descripción del workflow de GitHub Actions

Archivo: `.github/workflows/ci.yml`

Resumen de lo que hace el workflow:

- Se ejecuta en `push` y `pull_request` sobre `main`/`master`.
- Configura JDK 17 (`actions/setup-java@v4`).
- Hace cache del directorio Maven (`~/.m2/repository`).
- Ejecuta `checkstyle:check` (no bloqueante; el paso no falla la ejecución total).
- Ejecuta `mvn -B -DskipTests=false package` para compilar y ejecutar los tests.
- Construye una imagen Docker para pruebas locales con `docker/build-push-action@v4` (no hace `push`).

Puede modificar este workflow si desea añadir pasos como publicar la imagen en un registry, ejecutar escaneos de seguridad o desplegar en un entorno.

---

Si desea, puedo:

- Añadir una colección Postman de ejemplo y las instrucciones para importarla.
- Añadir badges de build/tests al `readme.md`.
- Generar un `Makefile` o scripts para simplificar los comandos comunes.

Dígame qué prefiere y lo añado.
