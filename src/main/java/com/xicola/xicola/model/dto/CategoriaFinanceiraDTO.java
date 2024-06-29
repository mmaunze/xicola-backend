package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.CategoriaFinanceira;
import lombok.Data;

@Data
public class CategoriaFinanceiraDTO {
    private Integer id;
    private String descricao;

    public CategoriaFinanceiraDTO(CategoriaFinanceira categoriaFinanceira) {
        this.id = categoriaFinanceira.getId();
        this.descricao = categoriaFinanceira.getDescricao();
    }
}
