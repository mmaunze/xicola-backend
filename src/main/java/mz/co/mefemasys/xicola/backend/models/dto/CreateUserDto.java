package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}
