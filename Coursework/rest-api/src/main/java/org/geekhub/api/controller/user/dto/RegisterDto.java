package org.geekhub.api.controller.user.dto;

import org.springframework.lang.NonNull;

public record RegisterDto(
    @NonNull String email,
    @NonNull String password
) {
}
