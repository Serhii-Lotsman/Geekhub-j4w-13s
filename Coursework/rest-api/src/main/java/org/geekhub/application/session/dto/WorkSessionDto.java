package org.geekhub.application.session.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public record WorkSessionDto(
    @NonNull Long id,
    @NonNull String email,
    @NonNull LocalDate date,
    @NonNull LocalTime timeBegin,
    @Nullable LocalTime timeEnd,
    @Nullable LocalTime totalTime,
    @Nullable String salary
) {
}
