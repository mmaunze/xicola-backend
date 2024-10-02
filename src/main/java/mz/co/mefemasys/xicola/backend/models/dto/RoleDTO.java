package mz.co.mefemasys.xicola.backend.models.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.ERole;
import mz.co.mefemasys.xicola.backend.models.Role;

@Data
public class RoleDTO {
    private Long id;
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = String.valueOf(role.getName());
    }
}
