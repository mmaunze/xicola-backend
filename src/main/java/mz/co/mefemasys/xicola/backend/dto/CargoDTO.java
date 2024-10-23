package mz.co.mefemasys.xicola.backend.dto;

import lombok.Getter;
import lombok.Setter;
import mz.co.mefemasys.xicola.backend.models.Cargo;

import java.util.logging.Logger;

@Getter
@Setter
public class CargoDTO {

    private static final Logger LOG = Logger.getLogger(CargoDTO.class.getName());

    private Long id;

    private String nome;

    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();

        this.nome = cargo.getDescricao();

    }
}
