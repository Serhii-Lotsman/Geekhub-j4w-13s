package org.geekhub.application.converter;

import org.geekhub.application.session.dto.WorkSessionDto;
import org.geekhub.application.session.dto.WorkStatisticDto;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.geekhub.application.session.model.WorkStatisticEntity;
import org.springframework.lang.NonNull;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
            workSessionEntity.getTotalTime(),
            getFormattedSalary(workSessionEntity.getSalary())
        );
    }

    public static WorkStatisticDto workStatisticToDto(@NonNull WorkStatisticEntity workStatisticEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM", Locale.ENGLISH);
        String formattedDate = workStatisticEntity.getDate().format(formatter);

        return new WorkStatisticDto(
            formattedDate,
            workStatisticEntity.getUserEmail(),
            workStatisticEntity.getMonthlyTotalTime(),
            getFormattedSalary(workStatisticEntity.getSalary())
        );
    }

    @NonNull
    private static String getFormattedSalary(Double workStatisticEntity) {
        if (workStatisticEntity == null) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(workStatisticEntity);
    }
}
