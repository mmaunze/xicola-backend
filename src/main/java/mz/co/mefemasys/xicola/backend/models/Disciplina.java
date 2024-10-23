package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "disciplina", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "disciplina_nome_disciplina_key", columnNames = {"nome_disciplina"})
})
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_disciplina", nullable = false, length = 100)
    private String nomeDisciplina;

}
