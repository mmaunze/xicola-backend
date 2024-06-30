package com.xicola.xicola.model.dto;

import java.math.BigDecimal;

import com.xicola.xicola.model.DetalheRelatorioFinanceiro;

import lombok.Data;

@Data
public class DetalheRelatorioFinanceiroDTO {
    private Integer id;
    private Integer relatorio;
    private String descricao;
    private BigDecimal valor;

    public DetalheRelatorioFinanceiroDTO(DetalheRelatorioFinanceiro detalhe) {
        this.id = detalhe.getId();
        this.relatorio = detalhe.getRelatorio().getId();
        this.descricao = detalhe.getDescricao();
        this.valor = detalhe.getValor();
    }
}
