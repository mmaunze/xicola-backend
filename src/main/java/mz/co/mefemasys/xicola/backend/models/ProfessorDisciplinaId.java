package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;

@Data
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
}