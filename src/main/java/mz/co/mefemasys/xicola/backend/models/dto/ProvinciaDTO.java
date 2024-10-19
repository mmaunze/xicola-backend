package mz.co.mefemasys.xicola.backend.models.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Provincia;

@Data
public class ProvinciaDTO {
    private Long id;
    private String nome;


    public ProvinciaDTO(Provincia provincia) {
        this.id = provincia.getId();
        this.nome = provincia.getNomeProvincia();

    }
}
