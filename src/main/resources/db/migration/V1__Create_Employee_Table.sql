CREATE TABLE employees
(
    id         SERIAL PRIMARY KEY NOT NULL,
    registry   VARCHAR(255)       NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    role       VARCHAR(255)       NOT NULL,
    created_at TIMESTAMP          NOT NULL,
    updated_at TIMESTAMP          NOT NULL
);