package com.xicola.xicola.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.xicola.xicola.model.Despesa;

import lombok.Data;

@Data
public class DespesaDTO {
    private Integer id;
    private String nome;
    private BigDecimal valor;
    private Instant data;
    private Integer categoria;
    private Long responsavel;
    private String estado;

    public DespesaDTO(Despesa despesa) {
        this.id = despesa.getId();
        this.nome = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getDataDespesa();
        this.categoria = despesa.getCategoria().getId();
        this.responsavel = despesa.getResponsavel().getId();
        this.estado = despesa.getEstado().getDescricao();
    }
}
