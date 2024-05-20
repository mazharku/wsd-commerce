CREATE TABLE user_role
(
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id        INT          NOT NULL,
    name      VARCHAR(128) NOT NULL,
    email     VARCHAR(256) NOT NULL,
    password  VARCHAR(256) NOT NULL,
    phone     VARCHAR(255) NULL,
    is_active BIT(1)       NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   INT          NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    is_active BIT(1)       NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
