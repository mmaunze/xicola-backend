package mz.co.mefemasys.xicola.backend.dto;

import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import mz.co.mefemasys.xicola.backend.models.Cargo;

@Getter
@Setter
public class CargoDTO {
    private Long id;
    private String nome;

    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();
        this.nome = cargo.getDescricao();
    }
    private static final Logger LOG = Logger.getLogger(CargoDTO.class.getName());
}
