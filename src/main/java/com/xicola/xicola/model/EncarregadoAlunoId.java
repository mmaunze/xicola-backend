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
public class EncarregadoAlunoId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -4054304937289448834L;
    @NotNull
    @Column(name = "encarregado", nullable = false)
    private Integer encarregado;

    @NotNull
    @Column(name = "aluno", nullable = false)
    private Integer aluno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EncarregadoAlunoId entity = (EncarregadoAlunoId) o;
        return Objects.equals(this.aluno, entity.aluno) &&
                Objects.equals(this.encarregado, entity.encarregado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, encarregado);
    }

}