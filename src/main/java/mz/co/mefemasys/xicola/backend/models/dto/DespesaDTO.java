package mz.co.mefemasys.xicola.backend.models.dto;

import java.math.BigDecimal;
import java.time.Instant;

import mz.co.mefemasys.xicola.backend.models.Despesa;

import lombok.Data;

@Data
public class DespesaDTO {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Instant data;
    private String categoria;
    private Long responsavel;
    private String estado;

    public DespesaDTO(Despesa despesa) {
        this.id = despesa.getId();
        this.nome = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getDataDespesa();
        this.categoria = despesa.getCategoria().getDescricao();
        this.responsavel = despesa.getResponsavel().getId();
        this.estado = despesa.getEstado().getDescricao();
    }
}
