package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Distrito;

import lombok.Data;

@Data
public class DistritoDTO {
    private Integer id;
    private String nome;
    private String provincia;

    public DistritoDTO (Distrito distrito) {
        this.id = distrito.getId();
        this.nome = distrito.getNomeDistrito();
        this.provincia = distrito.getProvincia().getNomeProvincia();
    }
}
