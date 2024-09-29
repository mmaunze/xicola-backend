package mz.co.mefemasys.xicola_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "disciplina_aluno", schema = "public")
public class DisciplinaAluno {
    @EmbeddedId
    private DisciplinaAlunoId id;

    @MapsId("disciplina")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @MapsId("aluno")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "aluno", nullable = false)
    private Aluno aluno;

    @Column(name = "ano_lectivo")
    private Integer anoLectivo;

    @NotNull
    @Column(name = "data_aula", nullable = false)
    private LocalDate dataAula;

    @NotNull
    @Column(name = "hora_aula", nullable = false)
    private LocalTime horaAula;

}