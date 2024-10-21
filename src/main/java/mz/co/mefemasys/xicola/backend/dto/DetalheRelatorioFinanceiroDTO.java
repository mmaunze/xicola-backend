package mz.co.mefemasys.xicola.backend.dto;

import java.math.BigDecimal;
import java.util.logging.Logger;
import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.DetalheRelatorioFinanceiro;

@Data
public class DetalheRelatorioFinanceiroDTO {
    private Long id;
    private Long relatorio;
    private String descricao;
    private BigDecimal valor;

    public DetalheRelatorioFinanceiroDTO(DetalheRelatorioFinanceiro detalhe) {
        this.id = detalhe.getId();
        this.relatorio = detalhe.getRelatorio().getId();
        this.descricao = detalhe.getDescricao();
        this.valor = detalhe.getValor();
    }
    private static final Logger LOG = Logger.getLogger(DetalheRelatorioFinanceiroDTO.class.getName());
}
