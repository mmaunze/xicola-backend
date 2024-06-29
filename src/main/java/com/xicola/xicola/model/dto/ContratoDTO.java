package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Contrato;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContratoDTO {
    private Integer id;
    private String descricao;
    private String tipo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;
    private String fornecedor;
    private Long responsavel;
    private String estado;

    public ContratoDTO(Contrato contrato) {
        this.id = contrato.getId();
        this.descricao = contrato.getDescricao();
        this.tipo = contrato.getTipo();
        this.dataInicio = contrato.getDataInicio();
        this.dataFim = contrato.getDataFim();
        this.valorTotal = contrato.getValorTotal();
        this.fornecedor = contrato.getFornecedor();
        this.responsavel = contrato.getResponsavel().getId();
        this.estado = contrato.getEstado().getDescricao(); 
    }
}
