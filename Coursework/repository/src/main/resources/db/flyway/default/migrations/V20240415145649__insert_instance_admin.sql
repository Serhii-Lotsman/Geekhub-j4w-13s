INSERT INTO users (email, password)
VALUES ('admin@gmail.com', '$2a$14$Yll8.zBd.LtzK1unnbTMROt7CPhM61Swp252qOuYNmr9GtW2c2vkS');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@gmail.com'),
        (SELECT id FROM roles WHERE name = 'ADMIN'));
