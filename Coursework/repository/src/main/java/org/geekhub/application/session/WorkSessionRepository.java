package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkSessionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class WorkSessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(WorkSessionRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WorkSessionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createWorkSession(String email) {
        String sql = """
            INSERT INTO work_session (
            user_email,
            date,
            begin_time
            ) VALUES (
            :userEmail,
            :date,
            :timeBegin
            )
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "userEmail", email,
                "date", LocalDate.now(),
                "timeBegin", LocalTime.now())
            );

        try {
            jdbcTemplate.update(sql, parameterSource);
            logger.info("Success to begin time for employee {}", email);
        } catch (DataAccessException e) {
            logger.error("Failed to begin time for employee {}. Error: {}", email, e.getMessage());
        }
    }

    public void closeWorkSession(long sessionId, LocalTime endTime, LocalTime totalTime) {
        String sql = """
            UPDATE work_session SET
            end_time = :endTime,
            total_time = :totalTime
            WHERE id = :sessionId
            AND date = :today
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "endTime", endTime,
                "totalTime", totalTime,
                "sessionId", sessionId,
                "today", LocalDate.now()
            ));

        try {
            jdbcTemplate.update(sql, parameterSource);
            logger.info("Success to end time for employee");
        } catch (DataAccessException e) {
            logger.error("Failed to end time for employee. Error: {}", e.getMessage());
        }
    }

    public List<WorkSessionEntity> findAllWorkSessionByEmail(String userEmail) {
        String query = "SELECT * FROM work_session WHERE user_email = :userEmail ORDER BY date DESC";
        List<WorkSessionEntity> workSessionEntityList = new ArrayList<>();

        SqlParameterSource parameterSource = new MapSqlParameterSource("userEmail", userEmail);

        try {
            workSessionEntityList = jdbcTemplate.query(
                query,
                parameterSource,
                WorkSessionMapper::mapWorkSession
            );
            logger.info("Work session by email {} found", userEmail);
        } catch (DataAccessException e) {
            logger.error("Failed to find sessions by email {}. Error: {}", userEmail, e.getMessage());
        }
        return workSessionEntityList;
    }

    public Optional<WorkSessionEntity> findTodayWorkSessionByEmail(String userEmail) {
        String query = """
            SELECT * FROM work_session
            WHERE user_email = :userEmail
            AND date = :today
            AND end_time IS NULL
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("userEmail", userEmail)
            .addValue("today", LocalDate.now());

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                query,
                parameterSource,
                WorkSessionMapper::mapWorkSession
            ));
        } catch (DataAccessException e) {
            logger.error("Failed to end time. Error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public List<WorkSessionEntity> findAllWorkSessions() {
        String query = "SELECT * FROM work_session ORDER BY date DESC";
        List<WorkSessionEntity> workSessionEntityList = new ArrayList<>();

        try {
            workSessionEntityList = jdbcTemplate.query(query, WorkSessionMapper::mapWorkSession);
            logger.info("Success to get work sessions");
        } catch (DataAccessException e) {
            logger.error("Failed to get work sessions. Error: {}", e.getMessage());
        }
        return workSessionEntityList;
    }

    public boolean existsStartedTimeByUserEmailOnToday(String email) {
        String query = """
            SELECT EXISTS(
            SELECT 1 FROM work_session
            WHERE user_email = :email
            AND date = :today
            AND end_time IS NULL)
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("email", email)
            .addValue("today", LocalDate.now());

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    public List<WorkSessionEntity> findAllOpenWorkSessions() {
        String query = """
            SELECT * FROM work_session
            WHERE end_time IS NULL
            AND DATE(date) != CURRENT_DATE
            ORDER BY date DESC;
            """;
        List<WorkSessionEntity> workSessionEntityList = new ArrayList<>();

        try {
            workSessionEntityList = jdbcTemplate.query(query, WorkSessionMapper::mapToUpdateWorkSession);
            logger.info("Open work sessions found");
        } catch (DataAccessException e) {
            logger.error("Failed to find open work sessions. Error: {}", e.getMessage());
        }
        return workSessionEntityList;
    }

    public Optional<WorkSessionEntity> findOpenWorkSessionBySessionId(long sessionId) {
        String query = """
            SELECT * FROM work_session
            WHERE id = :sessionId
            AND DATE(date) != CURRENT_DATE
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource("sessionId", sessionId);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                query,
                parameterSource,
                WorkSessionMapper::mapWorkSession
            ));
        } catch (DataAccessException e) {
            logger.error("Failed to find work session by id: {}. Error: {}", sessionId, e.getMessage());
            return Optional.empty();
        }
    }

    public void editOpenWorkSession(long sessionId, WorkSessionEntity workSessionEntity) {
        String sql = """
            UPDATE work_session SET
            end_time = :endTime,
            total_time = :totalTime
            WHERE id = :sessionId
            """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "endTime", Objects.requireNonNull(workSessionEntity.getTimeEnd()),
                "totalTime", Objects.requireNonNull(workSessionEntity.getTotalTime()),
                "sessionId", sessionId
            ));

        try {
            jdbcTemplate.update(sql, parameterSource);
            logger.info("Success to update end time for employee with session id {}", sessionId);
        } catch (DataAccessException e) {
            logger.error("Failed to update end time for employee with session id {}. Error: {}",
                sessionId, e.getMessage());
        }
    }
}
