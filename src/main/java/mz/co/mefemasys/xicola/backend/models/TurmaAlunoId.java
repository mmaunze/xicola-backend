package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class TurmaAlunoId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -1638347453636836111L;
    @NotNull
    @Column(name = "aluno", nullable = false)
    private Integer aluno;

    @NotNull
    @Column(name = "turma", nullable = false)
    private Integer turma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var entity = (TurmaAlunoId) o;
        return Objects.equals(this.aluno, entity.aluno) &&
                Objects.equals(this.turma, entity.turma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, turma);
    }

}