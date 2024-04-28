package org.geekhub.application.session.dto;

public record WorkStatisticDto(
    String date,
    String userEmail,
    String monthlyTotalTime
) {
}
