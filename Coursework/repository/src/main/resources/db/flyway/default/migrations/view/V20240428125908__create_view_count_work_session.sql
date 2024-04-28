CREATE VIEW monthly_time_session AS
SELECT to_char(date_trunc('month', s.date AT TIME ZONE 'UTC'),
               'YYYY-MM-dd') AS month,
       s.user_email       AS email,
       SUM(s.total_time)  AS total_time_per_month
FROM work_session s
GROUP BY month,
         s.user_email
ORDER BY month DESC
