package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.Cargo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoDTO {
    private Long id;
    private String nome;

    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();
        this.nome = cargo.getDescricao();
    }
}
