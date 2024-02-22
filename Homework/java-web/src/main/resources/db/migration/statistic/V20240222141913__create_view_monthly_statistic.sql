CREATE VIEW MonthlyEncodingStats AS
SELECT
    DATE_TRUNC('month', h.date) AS month,
    h.algorithm AS codec,
    COUNT(*) AS count
FROM
    history h
GROUP BY
    month,
    h.algorithm
