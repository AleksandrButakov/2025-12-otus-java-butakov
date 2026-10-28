CREATE SEQUENCE IF NOT EXISTS client_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS address_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS phone_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE client
(
    id   BIGINT DEFAULT nextval('client_seq') NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE address
(
    id        BIGINT DEFAULT nextval('address_seq') NOT NULL PRIMARY KEY,
    street    VARCHAR(255),
    client_id BIGINT UNIQUE,
    CONSTRAINT fk_address_client FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE phone
(
    id            BIGINT DEFAULT nextval('phone_seq') NOT NULL PRIMARY KEY,
    number        VARCHAR(50),
    client_id     BIGINT,
    client_id_key INT, -- Новая важная колонка для хранения индекса элементов списка в Spring Data JDBC!
    CONSTRAINT fk_phone_client FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);
