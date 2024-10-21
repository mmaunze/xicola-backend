package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Provincia;

import java.util.logging.Logger;

@Data
public class ProvinciaDTO {

    private static final Logger LOG = Logger.getLogger(ProvinciaDTO.class.getName());

    private Long id;

    private String nome;

    public ProvinciaDTO(Provincia provincia) {
        this.id = provincia.getId();

        this.nome = provincia.getNomeProvincia();

    }
}
