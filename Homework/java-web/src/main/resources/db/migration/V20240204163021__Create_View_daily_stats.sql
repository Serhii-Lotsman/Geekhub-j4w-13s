CREATE VIEW DailyEncodingStats AS
SELECT
    DATE_TRUNC('day', em.date) AS day,
    em.algorithm AS codec,
    em.status,
    COUNT(*) AS count
FROM
    encryption_message em
GROUP BY
    day,
    em.algorithm,
    em.status;
