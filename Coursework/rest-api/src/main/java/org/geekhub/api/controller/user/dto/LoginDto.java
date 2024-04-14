package org.geekhub.api.controller.user.dto;

import org.springframework.lang.NonNull;

public record LoginDto(
    @NonNull String email,
    @NonNull String password
) {
}
