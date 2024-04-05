INSERT INTO users (email, password)
VALUES ('super@gmail.com', 'superPassword');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'super@gmail.com'),
        (SELECT id FROM roles WHERE name = 'SUPER_ADMIN'));
