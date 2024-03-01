CREATE TABLE IF NOT EXISTS employees
(
    id         SERIAL PRIMARY KEY,
    full_name  VARCHAR(150),
    birthday   DATE,
    email      VARCHAR(150) UNIQUE,
    position   VARCHAR(100),
    password   VARCHAR(150),
    city       VARCHAR(100),
    is_married BOOLEAN,
    gender     VARCHAR(10)
)
