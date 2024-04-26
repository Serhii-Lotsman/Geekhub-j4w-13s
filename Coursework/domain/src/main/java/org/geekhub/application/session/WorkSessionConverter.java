package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkSessionEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkSessionConverter {

    private WorkSessionConverter() {
    }

    public static Map<String, Object> getSessionDetailsGroupByEmail(WorkSessionEntity employeeSession) {
        Map<String, Object> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("id", employeeSession.getId());
        sessionDetails.put("date", employeeSession.getDate());
        sessionDetails.put("start time", employeeSession.getTimeBegin());
        sessionDetails.put("end time", employeeSession.getTimeEnd());
        sessionDetails.put("total time", employeeSession.getTotalTime());
        return sessionDetails;
    }

    public static Map<String, Object> getSessionDetailsGroupByDate(WorkSessionEntity employeeSession) {
        Map<String, Object> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("id", employeeSession.getId());
        sessionDetails.put("start time", employeeSession.getTimeBegin());
        sessionDetails.put("end time", employeeSession.getTimeEnd());
        sessionDetails.put("total time", employeeSession.getTotalTime());
        return sessionDetails;
    }
}
