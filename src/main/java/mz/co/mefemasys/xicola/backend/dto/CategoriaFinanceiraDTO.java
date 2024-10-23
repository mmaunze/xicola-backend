package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.CategoriaFinanceira;

import java.util.logging.Logger;

@Data
public class CategoriaFinanceiraDTO {

    private static final Logger LOG = Logger.getLogger(CategoriaFinanceiraDTO.class.getName());

    private Long id;

    private String nome;

    public CategoriaFinanceiraDTO(CategoriaFinanceira categoriaFinanceira) {
        this.id = categoriaFinanceira.getId();

        this.nome = categoriaFinanceira.getDescricao();

    }
}
