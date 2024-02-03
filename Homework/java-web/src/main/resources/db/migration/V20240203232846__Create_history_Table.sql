CREATE TABLE history
(
    history_id SERIAL PRIMARY KEY,
    user_id    INTEGER,
    message_id INTEGER,
    timestamp  TIMESTAMP NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (message_id) REFERENCES encryption_message (id)
);
