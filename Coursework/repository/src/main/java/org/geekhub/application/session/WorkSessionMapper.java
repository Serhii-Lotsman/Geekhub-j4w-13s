package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkSessionEntity;
import org.geekhub.application.session.model.WorkStatisticEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("java:S1172")
public class WorkSessionMapper {

    private WorkSessionMapper() {
    }

    public static WorkSessionEntity mapWorkSession(ResultSet rs, int rowNum) throws SQLException {
        return new WorkSessionEntity(
            rs.getLong("id"),
            rs.getString("user_email"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("begin_time").toLocalTime(),
            rs.getTime("end_time") == null
                ? null
                : rs.getTime("end_time").toLocalTime(),
            rs.getTime("total_time") == null
                ? null
                : rs.getTime("total_time").toLocalTime()
        );
    }

    public static WorkSessionEntity mapToUpdateWorkSession(ResultSet rs, int rowNum) throws SQLException {
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

        return workSessionEntity;
    }

    public static WorkStatisticEntity mapWorkStatisticEntity(ResultSet rs, int rowNum) throws SQLException {
        WorkStatisticEntity workStatisticEntity = new WorkStatisticEntity();
        workStatisticEntity.setDate(rs.getDate("month").toLocalDate());
        workStatisticEntity.setUserEmail(rs.getString("email"));
        workStatisticEntity.setMonthlyTotalTime(rs.getString("total_time_per_month"));
        return workStatisticEntity;
    }
}
