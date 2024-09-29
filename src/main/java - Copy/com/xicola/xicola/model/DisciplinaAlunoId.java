package mz.co.mefemasys.xicola_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;

@Data
@Embeddable
public class DisciplinaAlunoId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 4668458589983488976L;
    @NotNull
    @Column(name = "disciplina", nullable = false)
    private Integer disciplina;

    @NotNull
    @Column(name = "aluno", nullable = false)
    private Integer aluno;

}