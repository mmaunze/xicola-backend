package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "horario", schema = "public")
public class Horario {

    private static final Logger LOG = Logger.getLogger(Horario.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sala", nullable = false)
    private Sala sala;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "turma", nullable = false)
    private Turma turma;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "professor", nullable = false)
    private Professor professor;

    @Size(max = 10)
    @NotNull
    @Column(name = "dia_semana", nullable = false, length = 10)
    private String diaSemana;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_termino", nullable = false)
    private LocalTime horaTermino;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
