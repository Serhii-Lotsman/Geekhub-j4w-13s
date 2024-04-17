package org.geekhub.application.session;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class WorkSessionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WorkSessionRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void insertWorkSession(LocalDateTime startTime) {
        String sql = "INSERT INTO work_sessions (start_time) VALUES (:startTime)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("startTime", startTime);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public void updateWorkSessionEndTime(Long id, LocalDateTime endTime, LocalDateTime totalTime) {
        String sql = """
            UPDATE work_sessions SET end_time = :endTime, total_time = :totalTime WHERE id = :id
            """;
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValues(Map.of(
                "id", id,
                "endTime", endTime,
                "totalTime", totalTime
            ));
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }
}
