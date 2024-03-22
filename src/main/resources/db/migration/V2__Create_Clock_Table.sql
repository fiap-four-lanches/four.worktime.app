CREATE TABLE clocks
(
    id                    SERIAL PRIMARY KEY NOT NULL,
    employee_id           INT                NOT NULL,
    clocked_time          TIMESTAMP          NOT NULL,
    clock_type            VARCHAR(255)       NOT NULL,
    was_manually_modified BOOLEAN            NOT NULL,
    created_at            TIMESTAMP          NOT NULL,
    updated_at            TIMESTAMP          NOT NULL
);