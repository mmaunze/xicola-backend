package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.DetalheRelatorioFinanceiro;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DetalheRelatorioFinanceiroDTO {
    private Integer id;
    private Integer relatorioId;
    private String descricao;
    private BigDecimal valor;

    public DetalheRelatorioFinanceiroDTO(DetalheRelatorioFinanceiro detalhe) {
        this.id = detalhe.getId();
        this.relatorioId = detalhe.getRelatorio().getId();
        this.descricao = detalhe.getDescricao();
        this.valor = detalhe.getValor();
    }
}
