package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departamento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "departamento_descricao_key", columnNames = { "descricao" }),
        @UniqueConstraint(name = "departamento_sigla_key", columnNames = { "sigla" })
})
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @Size(max = 10)
    @NotNull
    @Column(name = "sigla", nullable = false, length = 10)
    private String sigla;

}