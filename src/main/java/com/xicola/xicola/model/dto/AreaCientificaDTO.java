package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.AreaCientifica;

import lombok.Data;

@Data
public class AreaCientificaDTO {
    private Integer id;
    private String nome;

    public AreaCientificaDTO(AreaCientifica areaCientifica) {
        this.id = areaCientifica.getId();
        this.nome = areaCientifica.getDescricao();
    }
}
