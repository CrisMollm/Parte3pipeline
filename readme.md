# DockerPipeLine

## Descripción del Proyecto

DockerPipeLine es una aplicación Spring Boot que gestiona usuarios, productos y órdenes. Construida con Maven, utiliza Spring Data JPA para la persistencia de datos en PostgreSQL y está conteneurizada con Docker Compose para un despliegue sencillo.

La aplicación expone una API REST que devuelve datos en formato JSON, incluyendo datos de prueba precargados en la base de datos.

---

## Endpoints Disponibles

Todos los endpoints devuelven JSON.

### Usuarios
- `GET /api/users` — Obtiene la lista de todos los usuarios.

### Productos
- `GET /api/products` — Obtiene la lista de todos los productos.

### Órdenes
- `GET /api/orders` — Obtiene la lista de todas las órdenes.

---

## Cómo Usar

### Opción 1: Ejecutar con Docker Compose (Recomendado)

```bash
docker compose up --build
```

Esto construirá la imagen de la aplicación, levantará PostgreSQL y cargará los datos de prueba. La aplicación estará disponible en `http://localhost:8080`.

### Opción 2: Ejecutar localmente con Maven

```bash
mvn spring-boot:run
```

(Requiere Maven 3.6+ y Java 17+)

### Ejemplos de Uso

Obtener todos los usuarios:
```bash
curl http://localhost:8080/api/users
```

Obtener todos los productos:
```bash
curl http://localhost:8080/api/products
```

Obtener todas las órdenes:
```bash
curl http://localhost:8080/api/orders
```

Si quieres que ejecute la compilación y los tests aquí (si tu entorno lo permite), dime y lo intento.


