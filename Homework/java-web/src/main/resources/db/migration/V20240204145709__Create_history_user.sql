CREATE TABLE history
(
    user_id           SERIAL PRIMARY KEY,
    user_name         VARCHAR(100),
    user_email        VARCHAR(100),
    original_message  VARCHAR(255),
    encrypted_message VARCHAR(255),
    algorithm         VARCHAR(60),
    date              TIMESTAMP,
    status            VARCHAR(60)
);
