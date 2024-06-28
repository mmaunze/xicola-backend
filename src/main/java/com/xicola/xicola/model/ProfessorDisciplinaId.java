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
public class ProfessorDisciplinaId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -8221839168642199837L;
    @NotNull
    @Column(name = "professor", nullable = false)
    private Integer professor;

    @NotNull
    @Column(name = "disciplina", nullable = false)
    private Integer disciplina;

    @NotNull
    @Column(name = "classe", nullable = false)
    private Integer classe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProfessorDisciplinaId entity = (ProfessorDisciplinaId) o;
        return Objects.equals(this.professor, entity.professor) &&
                Objects.equals(this.classe, entity.classe) &&
                Objects.equals(this.disciplina, entity.disciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professor, classe, disciplina);
    }

}