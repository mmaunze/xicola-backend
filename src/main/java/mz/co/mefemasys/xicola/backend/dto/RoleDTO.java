package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Role;

import java.util.logging.Logger;

@Data
public class RoleDTO {

    private static final Logger LOG = Logger.getLogger(RoleDTO.class.getName());

    private Long id;

    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();

        this.name = String.valueOf(role.getName());

    }
}
