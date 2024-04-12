package org.geekhub.api.controller.user;

import org.springframework.lang.NonNull;

public record RegisterDto(
    @NonNull String email,
    @NonNull String password,
    @NonNull boolean enabled
){
}
