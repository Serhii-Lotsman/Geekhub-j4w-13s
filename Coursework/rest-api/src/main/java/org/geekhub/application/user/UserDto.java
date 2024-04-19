package org.geekhub.application.user;

import org.springframework.lang.NonNull;

public record UserDto(
    @NonNull String email,
    @NonNull String password
) {
}
