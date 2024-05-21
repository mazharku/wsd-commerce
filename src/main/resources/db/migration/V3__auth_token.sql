CREATE TABLE auth_token
(
    id                  INT AUTO_INCREMENT NOT NULL,
    user_id             INT                NOT NULL,
    token               VARCHAR(255)       NULL,
    token_creation_time datetime           NOT NULL,
    CONSTRAINT pk_auth_token PRIMARY KEY (id)
);

ALTER TABLE auth_token
    ADD CONSTRAINT FK_AUTH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);