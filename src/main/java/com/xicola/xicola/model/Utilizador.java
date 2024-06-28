package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "utilizador", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "utilizador_username_key", columnNames = {"username"})
})
public class Utilizador {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Size(max = 40)
    @NotNull
    @Column(name = "senha", nullable = false, length = 40)
    private String senha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_utilizador", nullable = false)
    private TipoPessoa tipoUtilizador;

}