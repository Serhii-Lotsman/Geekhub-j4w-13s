ALTER TABLE employee_card
    DROP CONSTRAINT fk_user_email,
    ADD CONSTRAINT fk_user_email FOREIGN KEY (user_email)
        REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE;
