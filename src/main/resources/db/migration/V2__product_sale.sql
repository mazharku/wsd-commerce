CREATE TABLE products
(
    id    INT          NOT NULL,
    name  VARCHAR(255) NOT NULL,
    price DOUBLE       NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE sales
(
    id           INT AUTO_INCREMENT NOT NULL,
    seller_id    INT                NOT NULL,
    customer_id  INT                NULL,
    product_id   INT                NULL,
    quantity     DOUBLE             NULL,
    total_amount DOUBLE             NULL,
    sale_at      date               NULL,
    CONSTRAINT pk_sales PRIMARY KEY (id)
);

