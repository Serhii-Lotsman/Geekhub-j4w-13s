CREATE TABLE work_sessions
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_time TIMESTAMP NOT NULL,
    end_time   TIMESTAMP,
    total_time INTERVAL
);
