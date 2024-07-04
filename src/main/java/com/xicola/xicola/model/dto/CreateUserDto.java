package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}
