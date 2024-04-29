CREATE VIEW monthly_time_session AS
SELECT to_char(date_trunc('month', ws.date AT TIME ZONE 'UTC'),
               'YYYY-MM-dd') AS month,
       ws.user_email          AS email,
       SUM(ws.total_time)     AS total_time_per_month,
       SUM(ws.salary)         AS salary_per_month
FROM work_session ws
GROUP BY month,
         ws.user_email
ORDER BY month DESC
