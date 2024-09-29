package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "encarregado_aluno", schema = "public")
public class EncarregadoAluno {
    @EmbeddedId
    private EncarregadoAlunoId id;

    @MapsId("encarregado")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "encarregado", nullable = false)
    private EncarregadoEducacao encarregado;

    @MapsId("aluno")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno", nullable = false)
    private Aluno aluno;

    @Size(max = 100)
    @NotNull
    @Column(name = "grau_parentesco", nullable = false, length = 100)
    private String grauParentesco;

}