package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.CategoriaFinanceira;

import lombok.Data;

@Data
public class CategoriaFinanceiraDTO {
    private Integer id;
    private String nome;

    public CategoriaFinanceiraDTO(CategoriaFinanceira categoriaFinanceira) {
        this.id = categoriaFinanceira.getId();
        this.nome = categoriaFinanceira.getDescricao();
    }
}
