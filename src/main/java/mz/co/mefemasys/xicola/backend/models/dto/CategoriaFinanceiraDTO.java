package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.CategoriaFinanceira;

import lombok.Data;

@Data
public class CategoriaFinanceiraDTO {
    private Long id;
    private String nome;

    public CategoriaFinanceiraDTO(CategoriaFinanceira categoriaFinanceira) {
        this.id = categoriaFinanceira.getId();
        this.nome = categoriaFinanceira.getDescricao();
    }
}
