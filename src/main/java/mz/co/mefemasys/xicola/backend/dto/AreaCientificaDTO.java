package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;

import java.util.logging.Logger;

@Data
public class AreaCientificaDTO {

    private static final Logger LOG = Logger.getLogger(AreaCientificaDTO.class.getName());

    private Long id;

    private String nome;

    public AreaCientificaDTO(AreaCientifica areaCientifica) {
        this.id = areaCientifica.getId();

        this.nome = areaCientifica.getDescricao();

    }
}
