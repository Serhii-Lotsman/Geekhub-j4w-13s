package org.geekhub.application.session;

import org.geekhub.application.session.dto.WorkSessionDto;
import org.geekhub.application.session.dto.WorkStatisticDto;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkSessionToMap {

    private WorkSessionToMap() {
    }

    public static Map<String, Object> getSessionDetailsGroupByEmail(WorkSessionDto workSessionDto) {
        Map<String, Object> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("id", workSessionDto.id());
        sessionDetails.put("date", workSessionDto.date());
        sessionDetails.put("start time", workSessionDto.timeBegin());
        sessionDetails.put("end time", workSessionDto.timeEnd());
        sessionDetails.put("total time", workSessionDto.totalTime());
        sessionDetails.put("salary", workSessionDto.salary());
        return sessionDetails;
    }

    public static Map<String, Object> getSessionDetailsGroupByDate(WorkSessionDto workSessionDto) {
        Map<String, Object> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("id", workSessionDto.id());
        sessionDetails.put("start time", workSessionDto.timeBegin());
        sessionDetails.put("end time", workSessionDto.timeEnd());
        sessionDetails.put("total time", workSessionDto.totalTime());
        sessionDetails.put("salary", workSessionDto.salary());
        return sessionDetails;
    }

    public static Map<String, String> getAllSessionStatisticGroupByDate(WorkStatisticDto workStatisticDto) {
        Map<String, String> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("email", workStatisticDto.userEmail());
        sessionDetails.put("monthly total time", workStatisticDto.monthlyTotalTime());
        sessionDetails.put("salary", workStatisticDto.salary());
        return sessionDetails;
    }

    public static Map<String, String> getUserSessionStatisticGroupByEmail(WorkStatisticDto workStatisticDto) {
        Map<String, String> sessionDetails = new LinkedHashMap<>();
        sessionDetails.put("date", workStatisticDto.date());
        sessionDetails.put("monthly total time", workStatisticDto.monthlyTotalTime());
        sessionDetails.put("salary", workStatisticDto.salary() == null ? null : workStatisticDto.salary());
        return sessionDetails;
    }
}
