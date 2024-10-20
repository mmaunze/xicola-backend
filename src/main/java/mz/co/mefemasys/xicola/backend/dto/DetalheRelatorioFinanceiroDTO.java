package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.DetalheRelatorioFinanceiro;

import java.math.BigDecimal;

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
}
