package org.geekhub.application.session;

import org.geekhub.application.exception.SessionException;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
        LocalTime totalTime = calculateTotalTime(workSessionEntity.getTimeBegin());
        workSessionRepository.closeWorkSession(workSessionEntity.getId(),
            LocalTime.now(),
            totalTime
        );
    }

    private LocalTime calculateTotalTime(LocalTime timeBegin) {
        LocalTime timeEnd = LocalTime.now();
        Duration duration = Duration.between(timeBegin, timeEnd);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return LocalTime.of((int) hours, (int) minutes);
    }

    public Map<String, List<Map<String, Object>>> getAllSessions() {
        return workSessionRepository.findAllWorkSessions().stream()
            .collect(Collectors.groupingBy(
                WorkSessionEntity::getEmail,
                Collectors.mapping(WorkSessionConverter::getSessionDetailsGroupByEmail, Collectors.toList())
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
                Collectors.mapping(WorkSessionConverter::getSessionDetailsGroupByDate, Collectors.toList())
            ));
    }
}
