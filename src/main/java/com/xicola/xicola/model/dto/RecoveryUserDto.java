package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Role;
import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {
}
