package mz.co.mefemasys.xicola.backend.models.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Role;
import mz.co.mefemasys.xicola.backend.models.Utilizador;

import java.util.Set;

@Data
public class UtilizadorDTO {
    private Long id;
    private String nome;
    private String username;
    private String email;
    private Set<Role> role;

    public UtilizadorDTO(Utilizador utilizador) {
        this.id = utilizador.getId();
        this.username = utilizador.getUsername();
        this.email = utilizador.getEmail();
        this.nome = utilizador.getNome() ;
        this.role = utilizador.getRoles();
    }
}
