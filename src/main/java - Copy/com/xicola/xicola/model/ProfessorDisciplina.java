package mz.co.mefemasys.xicola_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "professor_disciplina", schema = "public")
public class ProfessorDisciplina {
    @EmbeddedId
    private ProfessorDisciplinaId id;

    @MapsId("professor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "professor", nullable = false)
    private Professor professor;

    @MapsId("disciplina")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @Column(name = "ano_lectivo")
    private Integer anoLectivo;

}