package org.geekhub.application.converter;

import org.geekhub.application.session.dto.WorkSessionDto;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.springframework.lang.NonNull;

public class WorkSessionConverter {

    private WorkSessionConverter() {
    }

    public static WorkSessionDto workSessionToDto(@NonNull WorkSessionEntity workSessionEntity) {
        return new WorkSessionDto(
            workSessionEntity.getId(),
            workSessionEntity.getEmail(),
            workSessionEntity.getDate(),
            workSessionEntity.getTimeBegin(),
            workSessionEntity.getTimeEnd(),
            workSessionEntity.getTotalTime()
        );
    }
}
