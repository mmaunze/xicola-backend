package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "avaliacao", schema = "public")
public class Avaliacao {
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
    private static final Logger LOG = Logger.getLogger(Avaliacao.class.getName());

}