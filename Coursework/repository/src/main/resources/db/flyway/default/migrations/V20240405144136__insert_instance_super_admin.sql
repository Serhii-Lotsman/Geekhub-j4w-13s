INSERT INTO users (email, password)
VALUES ('super@gmail.com', '$2y$04$sj1r9Sz8SI3NKuuHprmLqu.o0GsfbaMHlA3Z4G/PQhxRW2UUZsSHK');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'super@gmail.com'),
        (SELECT id FROM roles WHERE name = 'SUPER_ADMIN'));
