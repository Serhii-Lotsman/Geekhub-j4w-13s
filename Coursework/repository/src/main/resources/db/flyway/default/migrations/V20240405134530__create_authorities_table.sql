CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(50) UNIQUE,
    authority VARCHAR(50),
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
