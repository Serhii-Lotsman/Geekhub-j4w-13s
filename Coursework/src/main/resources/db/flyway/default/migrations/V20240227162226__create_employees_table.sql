CREATE TABLE IF NOT EXISTS employees
(
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(150),
    birthday DATE,
    email VARCHAR(150) UNIQUE,
    city VARCHAR(100),
    position VARCHAR(100),
    password VARCHAR(150)
)
