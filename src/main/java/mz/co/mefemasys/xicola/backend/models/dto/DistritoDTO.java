package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.Distrito;

import lombok.Data;

@Data
public class DistritoDTO {
    private Long id;
    private String nome;
    private String provincia;

    public DistritoDTO (Distrito distrito) {
        this.id = distrito.getId();
        this.nome = distrito.getNomeDistrito();
        this.provincia = distrito.getProvincia().getNomeProvincia();
    }
}
