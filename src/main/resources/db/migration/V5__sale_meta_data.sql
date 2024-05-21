INSERT INTO products (id, name, price)
VALUES (1, 'T-Shirt', 19.99);
INSERT INTO products (id, name, price)
VALUES (2, 'Laptop', 799.99);
INSERT INTO products (id, name, price)
VALUES (3, 'Headphones', 49.99);
INSERT INTO products (id, name, price)
VALUES (4, 'Coffee Maker', 39.99);
INSERT INTO products (id, name, price)
VALUES (5, 'Smartwatch', 249.99);
INSERT INTO products (id, name, price)
VALUES (6, 'Book', 14.99);
INSERT INTO products (id, name, price)
VALUES (7, 'Water Bottle', 9.99);
INSERT INTO products (id, name, price)
VALUES (8, 'Desk Lamp', 24.99);
INSERT INTO products (id, name, price)
VALUES (9, 'Yoga Mat', 29.99);
INSERT INTO products (id, name, price)
VALUES (10, 'Speakers', 79.99);

-- sales meta data

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (1, 2, 1, 2, (19.99 * 2), '2024-02-15');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (3, 5, 2, 1, 799.99, '2024-02-15');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (2, 1, 3, 1, 49.99, '2024-03-10');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (1, 4, 4, 2, (39.99 * 2), '2024-03-10');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (3, 7, 5, 1, 249.99, '2024-04-05');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (2, 6, 6, 3, (14.99 * 3), '2024-04-05');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (1, 3, 7, 4, (9.99 * 4), '2024-04-18');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (2, 5, 8, 2, (24.99 * 2), '2024-04-18');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (3, 1, 9, 1, 29.99, '2024-05-02');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (1, 4, 10, 1, 79.99, '2024-05-02');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (2, 7, 3, 2, (49.99 * 2), '2024-03-22');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (1, 6, 5, 1, 249.99, '2024-04-12');

INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (3, 2, 7, 1, 9.99, '2024-04-25');

-- today sales
INSERT INTO sales (seller_id, customer_id, product_id, quantity, total_amount, sale_at)
VALUES (3, 2, 7, 2, (9.99 * 2), CURDATE());


