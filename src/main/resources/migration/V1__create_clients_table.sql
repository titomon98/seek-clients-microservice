CREATE TABLE clients
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255) NULL,
    surname    VARCHAR(255) NULL,
    age        INT NULL,
    birthday   date NULL,
    created_at date NULL,
    updated_at date NULL,
    CONSTRAINT pk_clients PRIMARY KEY (id)
);