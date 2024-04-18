CREATE TABLE IF NOT EXISTS employee_card
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    birthday   DATE        NOT NULL,
    user_email VARCHAR(30) UNIQUE,
    position   VARCHAR(30) NOT NULL,
    city       VARCHAR(30),
    is_married BOOLEAN,
    gender     VARCHAR(10),
    hire_date  DATE        NOT NULL,
    CONSTRAINT fk_user_email FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE
);
