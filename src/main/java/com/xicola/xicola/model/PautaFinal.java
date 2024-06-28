package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pauta_final", schema = "public")
public class PautaFinal {
    @Id
    @ColumnDefault("nextval('pauta_final_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "aluno", nullable = false)
    private Aluno aluno;

    @Column(name = "ano_lectivo")
    private Integer anoLectivo;

    @NotNull
    @Column(name = "nota_final", nullable = false)
    private Integer notaFinal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "professor", nullable = false)
    private Professor professor;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_publicacao")
    private Instant dataPublicacao;

    @Size(max = 30)
    @NotNull
    @Column(name = "resultado", nullable = false, length = 30)
    private String resultado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado_pauta", nullable = false)
    private Estado estadoPauta;

}