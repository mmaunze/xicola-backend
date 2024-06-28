package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Cargo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoDTO {
    private Integer id;
    private String descricao;

    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();
        this.descricao = cargo.getDescricao();
    }
}
