package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "detalhe_relatorio_financeiro", schema = "public")
public class DetalheRelatorioFinanceiro {

    private static final Logger LOG = Logger.getLogger(DetalheRelatorioFinanceiro.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "relatorio", nullable = false)
    private RelatorioFinanceiro relatorio;

    @NotNull
    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

    @NotNull
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

}
