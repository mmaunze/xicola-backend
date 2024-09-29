package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.AreaCientifica;

import lombok.Data;

@Data
public class AreaCientificaDTO {
    private Long id;
    private String nome;

    public AreaCientificaDTO(AreaCientifica areaCientifica) {
        this.id = areaCientifica.getId();
        this.nome = areaCientifica.getDescricao();
    }
}
