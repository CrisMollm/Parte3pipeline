-- Clear tables (safe for H2 and Postgres during tests)
SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE orders;
TRUNCATE TABLE products;
TRUNCATE TABLE users;
SET REFERENTIAL_INTEGRITY TRUE;

-- Users
INSERT INTO users (id, name, email) VALUES (1, 'Alice', 'alice@example.com');
INSERT INTO users (id, name, email) VALUES (2, 'Bob', 'bob@example.com');
INSERT INTO users (id, name, email) VALUES (3, 'Carol', 'carol@example.com');
INSERT INTO users (id, name, email) VALUES (4, 'Dave', 'dave@example.com');
INSERT INTO users (id, name, email) VALUES (5, 'Eve', 'eve@example.com');
INSERT INTO users (id, name, email) VALUES (6, 'Frank', 'frank@example.com');
INSERT INTO users (id, name, email) VALUES (7, 'Grace', 'grace@example.com');
INSERT INTO users (id, name, email) VALUES (8, 'Heidi', 'heidi@example.com');
INSERT INTO users (id, name, email) VALUES (9, 'Ivan', 'ivan@example.com');
INSERT INTO users (id, name, email) VALUES (10, 'Judy', 'judy@example.com');

-- Products
INSERT INTO products (id, name, price) VALUES (1, 'Widget A', 9.99);
INSERT INTO products (id, name, price) VALUES (2, 'Widget B', 19.99);
INSERT INTO products (id, name, price) VALUES (3, 'Widget C', 29.99);
INSERT INTO products (id, name, price) VALUES (4, 'Gadget X', 14.50);
INSERT INTO products (id, name, price) VALUES (5, 'Gadget Y', 24.50);
INSERT INTO products (id, name, price) VALUES (6, 'Thing 1', 5.00);
INSERT INTO products (id, name, price) VALUES (7, 'Thing 2', 7.50);
INSERT INTO products (id, name, price) VALUES (8, 'Thing 3', 12.00);
INSERT INTO products (id, name, price) VALUES (9, 'Item Z', 99.99);
INSERT INTO products (id, name, price) VALUES (10, 'Item Q', 49.95);

-- Orders (simple mapping using existing ids)
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (1, 1, 1, 2);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (2, 2, 3, 1);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (3, 3, 2, 5);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (4, 4, 4, 1);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (5, 5, 5, 3);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (6, 6, 6, 10);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (7, 7, 7, 2);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (8, 8, 8, 4);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (9, 9, 9, 1);
INSERT INTO orders (id, user_id, product_id, quantity) VALUES (10, 10, 10, 2);

-- Ensure identity counters are beyond inserted IDs (H2)
ALTER TABLE users ALTER COLUMN id RESTART WITH 11;
ALTER TABLE products ALTER COLUMN id RESTART WITH 11;
ALTER TABLE orders ALTER COLUMN id RESTART WITH 11;
