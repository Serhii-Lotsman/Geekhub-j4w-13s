package org.geekhub.application.session.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record WorkStatisticDto(
    @NonNull String date,
    @NonNull String userEmail,
    @Nullable String monthlyTotalTime,
    @Nullable String salary
) {
}
