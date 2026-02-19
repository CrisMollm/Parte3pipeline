-- 1. Borrar tablas y secuencias si existen
DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS products_seq;
DROP SEQUENCE IF EXISTS orders_seq;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 2. Crear secuencias de Hibernate
CREATE SEQUENCE users_seq START WITH 11;
CREATE SEQUENCE products_seq START WITH 11;
CREATE SEQUENCE orders_seq START WITH 11;

-- 3. Crear tablas
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10,2)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    product_id BIGINT REFERENCES products(id),
    quantity INTEGER
);

-- 3. Insertar 10 Usuarios
INSERT INTO users (id, name, email) VALUES 
(1, 'Alice', 'alice@example.com'),
(2, 'Bob', 'bob@example.com'),
(3, 'Carol', 'carol@example.com'),
(4, 'Dave', 'dave@example.com'),
(5, 'Eve', 'eve@example.com'),
(6, 'Frank', 'frank@example.com'),
(7, 'Grace', 'grace@example.com'),
(8, 'Heidi', 'heidi@example.com'),
(9, 'Ivan', 'ivan@example.com'),
(10, 'Judy', 'judy@example.com');

-- 4. Insertar 10 Productos
INSERT INTO products (id, name, price) VALUES 
(1, 'Widget A', 9.99),
(2, 'Widget B', 19.99),
(3, 'Widget C', 29.99),
(4, 'Gadget X', 14.50),
(5, 'Gadget Y', 24.50),
(6, 'Thing 1', 5.00),
(7, 'Thing 2', 7.50),
(8, 'Thing 3', 12.00),
(9, 'Item Z', 99.99),
(10, 'Item Q', 49.95);

-- 5. Insertar 10 Pedidos
INSERT INTO orders (id, user_id, product_id, quantity) VALUES 
(1, 1, 1, 2),
(2, 2, 3, 1),
(3, 3, 2, 5),
(4, 4, 4, 1),
(5, 5, 5, 3),
(6, 6, 6, 10),
(7, 7, 7, 2),
(8, 8, 8, 4),
(9, 9, 9, 1),
(10, 10, 10, 2);