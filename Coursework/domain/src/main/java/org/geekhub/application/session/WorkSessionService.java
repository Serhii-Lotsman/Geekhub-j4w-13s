package org.geekhub.application.session;

import org.geekhub.application.exception.SessionException;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.geekhub.application.validation.WorkSessionValidation;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;

    public WorkSessionService(WorkSessionRepository workSessionRepository) {
        this.workSessionRepository = workSessionRepository;
    }

    public void beginWorkSession(String email) {
        if (workSessionRepository.existsStartedTimeByUserEmailOnToday(email)) {
            throw new SessionException("The working day has already started");
        }

        workSessionRepository.createWorkSession(email);
    }

    public void endWorkSession(String email) {
        WorkSessionEntity workSessionEntity = getTodayWorkSessionByEmail(email);
        if (workSessionEntity.getTimeEnd() != null) {
            throw new SessionException("The working day doesn't started");
        }
        LocalTime totalTime = calculateTotalTime(workSessionEntity.getTimeBegin(), LocalTime.now(ZoneId.of("UTC")));
        workSessionRepository.closeWorkSession(workSessionEntity.getId(),
            LocalTime.now(ZoneId.of("UTC")),
            totalTime
        );
    }

    public Map<String, List<Map<String, Object>>> getAllSessions(int pageNum, int pageSize) {
        return workSessionRepository.findAllWorkSessions(pageNum, pageSize).stream()
            .collect(Collectors.groupingBy(
                WorkSessionEntity::getEmail,
                Collectors.mapping(WorkSessionToMap::getSessionDetailsGroupByEmail, Collectors.toList())
            ));
    }

    public WorkSessionEntity getTodayWorkSessionByEmail(String email) {
        return workSessionRepository.findTodayWorkSessionByEmail(email)
            .orElseThrow(() -> new SessionException("Failed to find session for today"));
    }

    public Map<LocalDate, List<Map<String, Object>>> getAllWorkSessionByEmail(String email) {
        return workSessionRepository.findAllWorkSessionByEmail(email).stream()
            .collect(Collectors.groupingBy(
                WorkSessionEntity::getDate,
                Collectors.mapping(WorkSessionToMap::getSessionDetailsGroupByDate, Collectors.toList())
            ));
    }

    public void editWorkSession(long sessionId, WorkSessionEntity workSessionEntity) {
        LocalTime totalTime = calculateTotalTime(
            workSessionEntity.getTimeBegin(),
            workSessionEntity.getTimeEnd()
        );
        workSessionEntity.setTotalTime(totalTime);
        workSessionRepository.editOpenWorkSession(sessionId, workSessionEntity);
    }

    public WorkSessionEntity getOpenWorkSession(long sessionId) {
        return workSessionRepository.findOpenWorkSessionBySessionId(sessionId)
            .orElseThrow(() -> new SessionException("Failed to find work session by id " + sessionId));
    }

    public List<WorkSessionEntity> getAllOpenWorkSessions(int pageNum, int pageSize) {
        return workSessionRepository.findAllOpenWorkSessions(pageNum, pageSize);
    }

    private LocalTime calculateTotalTime(LocalTime timeBegin, LocalTime timeEnd) {
        WorkSessionValidation.sequenceOfTimeValidation(timeBegin, timeEnd);
        Duration duration = Duration.between(timeBegin, timeEnd);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return LocalTime.of((int) hours, (int) minutes);
    }
}
