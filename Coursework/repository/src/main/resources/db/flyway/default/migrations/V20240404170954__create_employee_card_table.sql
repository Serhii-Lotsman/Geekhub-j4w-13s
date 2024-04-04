CREATE TABLE IF NOT EXISTS employee_card
(
    id         SERIAL PRIMARY KEY,
    full_name  VARCHAR(150)        NOT NULL,
    birthday   DATE                NOT NULL,
    email      VARCHAR(150) UNIQUE NOT NULL,
    position   VARCHAR(100)        NOT NULL,
    city       VARCHAR(100),
    is_married BOOLEAN,
    gender     VARCHAR(10),
    hire_date  TIMESTAMP           NOT NULL
)
