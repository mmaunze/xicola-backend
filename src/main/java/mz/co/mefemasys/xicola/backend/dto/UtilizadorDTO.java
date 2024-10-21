package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Role;
import mz.co.mefemasys.xicola.backend.models.Utilizador;

import java.util.Set;
import java.util.logging.Logger;

@Data
public class UtilizadorDTO {
    private static final Logger LOG = Logger.getLogger(UtilizadorDTO.class.getName());
    private Long id;
    private String nome;
    private String username;
    private String email;
    private String role; // Alterado para String

    public UtilizadorDTO(Utilizador utilizador) {
        this.id = utilizador.getId();
        this.username = utilizador.getUsername();
        this.email = utilizador.getEmail();
        this.nome = utilizador.getNome();
        this.role = getSingleRoleAsString(utilizador.getRoles());
    }

    // Método para obter uma única role como string
    private String getSingleRoleAsString(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return ""; // Retorna uma string vazia se não houver roles
        }

        // Obtém a primeira role disponível
        Role firstRole = roles.iterator().next();
        return mapRoleToString(firstRole); // Mapeia a role para string
    }

    // Método que usa switch para mapear a role para string
    private String mapRoleToString(Role role) {
        if (role == null) {
            return ""; // Retorna uma string vazia se a role for nula
        }

        switch (role.getName()) {
            case ROLE_ESTUDANTE:
                return "Estudante";
            case ROLE_ADMIN:
                return "Admin";
            case ROLE_MODERATOR:
                return "Moderador";
            case ROLE_PROFESSOR:
                return "Professor";
            case ROLE_BIBLIOTECARIO:
                return "Bibliotecário";
            case ROLE_FINANCEIRO:
                return "Financeiro";
            case ROLE_PEDAGOGICO:
                return "Pedagógico";
            case ROLE_AQUISICOES:
                return "Aquisicoes";
            case ROLE_DIRECTOR:
                return "Director";
            case ROLE_ENCARREGADO:
                return "Encarregado";
            default:
                return ""; // Retorna uma string vazia se a role não corresponder
        }
    }
}
