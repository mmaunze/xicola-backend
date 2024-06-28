package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "turma_aluno", schema = "public")
public class TurmaAluno {
    @EmbeddedId
    private TurmaAlunoId id;

    @MapsId("aluno")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "aluno", nullable = false)
    private Aluno aluno;

    @MapsId("turma")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "turma", nullable = false)
    private Turma turma;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private Integer anoLectivo;

}