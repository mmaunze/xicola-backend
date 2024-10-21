package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "avaliacao", schema = "public")
public class Avaliacao {

    private static final Logger LOG = Logger.getLogger(Avaliacao.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tipo_avaliacao", nullable = false)
    private TipoAvaliacao tipoAvaliacao;

    @NotNull
    @Column(name = "trimestre", nullable = false)
    private Integer trimestre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @NotNull
    @Column(name = "observacao", nullable = false, length = Integer.MAX_VALUE)
    private String observacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
