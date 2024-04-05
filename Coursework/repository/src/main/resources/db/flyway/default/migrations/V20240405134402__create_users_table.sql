CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(150)        NOT NULL
);
