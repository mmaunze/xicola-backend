package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "comunicados", schema = "public")
public class Comunicado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @NotNull
    @Column(name = "conteudo", nullable = false, length = Integer.MAX_VALUE)
    private String conteudo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_publicacao", nullable = false)
    private Instant dataPublicacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "responsavel", nullable = false)
    private Funcionario responsavel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "destinatario", nullable = false)
    private TipoPessoa destinatario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;
    private static final Logger LOG = Logger.getLogger(Comunicado.class.getName());

}