INSERT INTO history
(user_id, user_name, user_email, original_message, encrypted_message, algorithm, date, status)
SELECT u.user_id,
       u.name  AS user_name,
       u.email AS user_email,
       em.original_message,
       em.encrypted_message,
       em.algorithm,
       em.date,
       em.status
FROM encryption_message em
         LEFT JOIN
     users u ON u.user_id = em.user_id;
