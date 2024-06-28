package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.AreaCientifica;
import lombok.Data;

@Data
public class AreaCientificaDTO {
    private Integer id;
    private String descricao;

    public AreaCientificaDTO(AreaCientifica areaCientifica) {
        this.id = areaCientifica.getId();
        this.descricao = areaCientifica.getDescricao();
    }
}
