package org.geekhub.application.auth.dto;

import org.springframework.lang.NonNull;

public record RegisterDto(
    @NonNull String email,
    @NonNull String password,
    @NonNull String confirmPassword
) {
}
