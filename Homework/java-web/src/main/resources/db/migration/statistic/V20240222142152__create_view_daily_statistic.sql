CREATE VIEW DailyEncodingStats AS
SELECT
    DATE_TRUNC('day', h.date) AS day,
    h.algorithm AS codec,
    COUNT(*) AS count
FROM
    history h
GROUP BY
    day,
    h.algorithm;
