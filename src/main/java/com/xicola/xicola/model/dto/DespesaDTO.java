package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Despesa;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class DespesaDTO {
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private Instant dataDespesa;
    private Integer categoria;
    private Long responsavel;
    private String estado;

    public DespesaDTO(Despesa despesa) {
        this.id = despesa.getId();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.dataDespesa = despesa.getDataDespesa();
        this.categoria = despesa.getCategoria().getId();
        this.responsavel = despesa.getResponsavel().getId();
        this.estado = despesa.getEstado().getDescricao();
    }
}
