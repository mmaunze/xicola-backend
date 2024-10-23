package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "aula", schema = "public")
public class Aula {

    private static final Logger LOG = Logger.getLogger(Aula.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "disciplina", nullable = false)
    private Disciplina disciplina;

    @Size(max = 255)
    @Column(name = "titulo")
    private String titulo;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private Integer anoLectivo;

    @NotNull
    @Column(name = "classe", nullable = false)
    private Integer classe;

    @NotNull
    @Column(name = "resumo", nullable = false, length = Integer.MAX_VALUE)
    private String resumo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_aula", nullable = false)
    private Instant dataAula;

    @NotNull
    @Column(name = "conteudo", nullable = false, length = Integer.MAX_VALUE)
    private String conteudo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
