package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

import java.time.Instant;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "transacao", schema = "public")
public class Transacao {

    private static final Logger LOG = Logger.getLogger(Transacao.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipoTransacao;

    @NotNull
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_transacao", nullable = false)
    private Instant dataTransacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categoria", nullable = false)
    private CategoriaFinanceira categoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "responsavel", nullable = false)
    private Funcionario responsavel;

    @NotNull
    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
