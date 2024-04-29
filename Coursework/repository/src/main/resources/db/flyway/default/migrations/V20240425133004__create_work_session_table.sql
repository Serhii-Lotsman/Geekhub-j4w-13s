CREATE TABLE work_session
(
    id         SERIAL PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    date       DATE         NOT NULL,
    begin_time TIME         NOT NULL,
    end_time   TIME,
    total_time INTERVAL,
    salary     DECIMAL,
    FOREIGN KEY (user_email)
        REFERENCES users (email)
        ON DELETE CASCADE ON UPDATE CASCADE
);
