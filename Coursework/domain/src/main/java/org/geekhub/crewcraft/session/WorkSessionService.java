package org.geekhub.crewcraft.session;

import org.geekhub.repository.session.WorkSessionRepository;

import java.time.LocalDateTime;

public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;

    public WorkSessionService(WorkSessionRepository workSessionRepository) {
        this.workSessionRepository = workSessionRepository;
    }

    public void endWorkSession(Long workSessionId, LocalDateTime endTime, LocalDateTime totalTime) {
        workSessionRepository.updateWorkSessionEndTime(workSessionId, endTime, totalTime);
    }

}
