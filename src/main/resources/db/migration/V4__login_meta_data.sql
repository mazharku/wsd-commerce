/*roles*/

INSERT INTO roles (id, role_name, is_active)
SELECT 1, 'ADMIN', 1
FROM DUAL WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 1);

INSERT INTO roles (id, role_name, is_active)
SELECT 2, 'CASHIER', 1
FROM DUAL WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (id, role_name, is_active)
SELECT 3, 'CUSTOMER', 1
FROM DUAL WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 3);

/*admin pass 123456*/

INSERT INTO users (id, name, email, password, phone, is_active)
VALUES (1,'Admin User', 'admin@example.com', '$2a$12$ts1sfK9bewfU.e5sEf7weO0JeVAjcuVmSu5gGbXGvRCqE/iaQ8Qr6', '1234567890', 1);

INSERT INTO user_role (role_id, user_id)
SELECT 1, 1;

/*cashier */

INSERT INTO users (id, name, email, password, phone, is_active)
VALUES (2,'Cashier User', 'cashier@example.com', '$2a$12$ts1sfK9bewfU.e5sEf7weO0JeVAjcuVmSu5gGbXGvRCqE/iaQ8Qr6', '9876543210', 1);

INSERT INTO user_role (role_id, user_id)
SELECT 2, 2;

/*customer 1*/

INSERT INTO users (id,name, email, password, phone, is_active)
VALUES (3,'Customer 1', 'customer1@example.com', '$2a$12$ts1sfK9bewfU.e5sEf7weO0JeVAjcuVmSu5gGbXGvRCqE/iaQ8Qr6', '123', 1);

INSERT INTO user_role (role_id, user_id)
SELECT 3, 3;

/*customer 2*/

INSERT INTO users (id,name, email, password, phone, is_active)
VALUES (4,'Customer 2', 'customer1@example.com', '$2a$12$ts1sfK9bewfU.e5sEf7weO0JeVAjcuVmSu5gGbXGvRCqE/iaQ8Qr6', '124', 1);

INSERT INTO user_role (role_id, user_id)
SELECT 3, 4;

/*customer 3*/

INSERT INTO users (id,name, email, password, phone, is_active)
VALUES (5,'Customer 3', 'customer1@example.com', '$2a$12$ts1sfK9bewfU.e5sEf7weO0JeVAjcuVmSu5gGbXGvRCqE/iaQ8Qr6', '125', 1);

INSERT INTO user_role (role_id, user_id)
SELECT 3, 5;




