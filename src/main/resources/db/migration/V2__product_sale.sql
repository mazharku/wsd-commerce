CREATE TABLE products
(
    id      INT          NOT NULL,
    name    VARCHAR(255) NOT NULL,
    price DOUBLE NULL,
    sale_id INT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE sales
(
    id          INT AUTO_INCREMENT NOT NULL,
    seller_id   INT NOT NULL,
    customer_id INT NULL,
    quantity DOUBLE NULL,
    total_amount DOUBLE NULL,
    sale_at     datetime NULL,
    CONSTRAINT pk_sales PRIMARY KEY (id)
);

ALTER TABLE sales
    ADD CONSTRAINT FK_SALES_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE sales
    ADD CONSTRAINT FK_SALES_ON_SELLER FOREIGN KEY (seller_id) REFERENCES users (id);

