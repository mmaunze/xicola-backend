package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Ativo;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AtivoDTO {
    private Integer id;
    private String descricao;
    private String tipo;
    private LocalDate dataAquisicao;
    private BigDecimal valorAquisicao;
    private String localizacao;
    private String estado;

    public AtivoDTO(Ativo ativo) {
        this.id = ativo.getId();
        this.descricao = ativo.getDescricao();
        this.tipo = ativo.getTipo();
        this.dataAquisicao = ativo.getDataAquisicao();
        this.valorAquisicao = ativo.getValorAquisicao();
        this.localizacao = ativo.getLocalizacao();
        this.estado = ativo.getEstado().getDescricao();
    }
}
