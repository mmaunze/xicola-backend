package mz.co.mefemasys.xicola.backend.dto;

import lombok.Getter;

import lombok.Setter;

import mz.co.mefemasys.xicola.backend.models.Estado;

import java.util.logging.Logger;

@Getter
@Setter
public class EstadoDTO {

    private static final Logger LOG = Logger.getLogger(EstadoDTO.class.getName());

    private Long id;

    private String descricao;

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();

        this.descricao = estado.getDescricao();

    }
}
