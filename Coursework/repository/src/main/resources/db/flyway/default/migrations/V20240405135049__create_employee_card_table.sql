CREATE TABLE IF NOT EXISTS employee_card
(
    id         SERIAL PRIMARY KEY,
    full_name  VARCHAR(150) NOT NULL,
    birthday   DATE         NOT NULL,
    user_email VARCHAR(150) UNIQUE,
    position   VARCHAR(100) NOT NULL,
    city       VARCHAR(100),
    is_married BOOLEAN,
    gender     VARCHAR(10),
    hire_date  DATE         NOT NULL,
    CONSTRAINT fk_user_email FOREIGN KEY (user_email) REFERENCES users (username) ON DELETE CASCADE
);
