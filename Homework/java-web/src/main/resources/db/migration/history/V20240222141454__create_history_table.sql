CREATE TABLE IF NOT EXISTS history
(
    id                SERIAL PRIMARY KEY,
    user_id           INTEGER      NOT NULL REFERENCES users (user_id),
    original_message  VARCHAR(255) NOT NULL,
    encrypted_message VARCHAR(255) NOT NULL,
    algorithm         VARCHAR(50)  NOT NULL,
    operation         VARCHAR(50)  NOT NULL,
    date              TIMESTAMP    NOT NULL
);
