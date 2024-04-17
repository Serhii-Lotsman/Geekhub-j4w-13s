package org.geekhub.application.auth.dto;

import org.springframework.lang.NonNull;

public record LoginDto(
    @NonNull String email,
    @NonNull String password
) {
}
