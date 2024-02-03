CREATE TABLE encryption_message
(
    id                SERIAL PRIMARY KEY,
    user_id           INTEGER      NOT NULL,
    original_message  VARCHAR(255) NOT NULL,
    encrypted_message VARCHAR(255) NOT NULL,
    algorithm         VARCHAR(60)  NOT NULL,
    date              TIMESTAMP    NOT NULL
);
