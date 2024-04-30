CREATE TABLE IF NOT EXISTS persistent_logins
(
    username  VARCHAR(255) NOT NULL,
    series    VARCHAR(64) PRIMARY KEY,
    token     VARCHAR(255) NOT NULL,
    last_used TIMESTAMP    NOT NULL
);
