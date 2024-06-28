package com.xicola.xicola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DisciplinaAlunoId entity = (DisciplinaAlunoId) o;
        return Objects.equals(this.aluno, entity.aluno) &&
                Objects.equals(this.disciplina, entity.disciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, disciplina);
    }

}