package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkStatisticEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkStatisticRepository {

    private static final Logger logger = LoggerFactory.getLogger(WorkStatisticRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WorkStatisticRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WorkStatisticEntity> findAllMonthlyStatistic() {
        String query = "SELECT * FROM monthly_time_session ORDER BY month DESC";

        List<WorkStatisticEntity> allStatisticEntityList = new ArrayList<>();

        try {
            allStatisticEntityList = jdbcTemplate.query(
                query,
                WorkSessionMapper::mapWorkStatisticEntity
            );
            logger.info("All work statistic found");
        } catch (DataAccessException e) {
            logger.error("Failed to find all work statistic. Error: {}", e.getMessage());
        }

        return allStatisticEntityList;
    }

    public List<WorkStatisticEntity> findUserMonthlyStatisticByEmail(String email) {
        String query = """
        SELECT * FROM monthly_time_session
        WHERE email = :email
        ORDER BY month DESC
        """;
        List<WorkStatisticEntity> workStatisticEntityList = new ArrayList<>();

        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        try {
            workStatisticEntityList = jdbcTemplate.query(
                query,
                parameterSource,
                WorkSessionMapper::mapWorkStatisticEntity
            );
            logger.info("Work session statistic by email {} found", email);
        } catch (DataAccessException e) {
            logger.error("Failed to find sessions statistic by email {}. Error: {}", email, e.getMessage());
        }

        return workStatisticEntityList;
    }
}
