package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "utilizador",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Utilizador {

    @Id
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "utilizador_role", joinColumns = @JoinColumn(name = "utilizador"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles = new HashSet<>();

    public Utilizador() {
    }

    public Utilizador(String username, String nome, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
    }

}
