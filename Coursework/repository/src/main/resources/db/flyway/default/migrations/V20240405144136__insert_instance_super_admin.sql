INSERT INTO users (email, password)
VALUES ('super@gmail.com', '$2a$14$AiheJUSxRLWFhwVF.k32GO8.esw0B55tXQM1FoxLHzi/K/XKDy08K');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'super@gmail.com'),
        (SELECT id FROM roles WHERE name = 'SUPER_ADMIN'));
