CREATE VIEW MonthlyEncodingStats AS
SELECT
    DATE_TRUNC('month', em.date) AS month,
    em.algorithm AS codec,
    em.status,
    COUNT(*) AS count
FROM
    encryption_message em
GROUP BY
    month,
    em.algorithm,
    em.status;
