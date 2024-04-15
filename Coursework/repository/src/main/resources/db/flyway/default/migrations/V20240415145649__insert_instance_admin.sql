INSERT INTO users (email, password)
VALUES ('admin@gmail.com', '$2a$04$552lkbC5sscGwHeH/ORHJ.Juz4qyhX86s/XO3wkgtXlAKJlPHwcHi');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@gmail.com'),
        (SELECT id FROM roles WHERE name = 'ADMIN'));
