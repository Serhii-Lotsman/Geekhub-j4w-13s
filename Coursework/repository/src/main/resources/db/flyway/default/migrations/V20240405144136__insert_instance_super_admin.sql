INSERT INTO users (email, password)
VALUES ('super@gmail.com', '$2a$04$6MXEGXfbHOTzstgrXEkxJ.2hg8tnlcfeV.C1jw/3e5wOzHW/3lCLO');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'super@gmail.com'),
        (SELECT id FROM roles WHERE name = 'SUPER_ADMIN'));
