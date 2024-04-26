package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkSessionEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkSessionMapper {

    private WorkSessionMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static WorkSessionEntity mapWorkSession(ResultSet rs, int rowNum) throws SQLException {
        WorkSessionEntity workSessionEntity = new WorkSessionEntity();
        workSessionEntity.setId(rs.getLong("id"));
        workSessionEntity.setEmail(rs.getString("user_email"));
        workSessionEntity.setDate(rs.getDate("date").toLocalDate());
        workSessionEntity.setTimeBegin(rs.getTime("begin_time").toLocalTime());

        workSessionEntity.setTimeEnd(
            rs.getTime("end_time") == null
            ? null
            : rs.getTime("end_time").toLocalTime()
        );

        workSessionEntity.setTotalTime(
            rs.getTime("total_time") == null
            ? null
            : rs.getTime("total_time").toLocalTime()
        );

        return workSessionEntity;
    }
}
