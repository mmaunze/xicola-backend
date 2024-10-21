package mz.co.mefemasys.xicola.backend.dto;

import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import mz.co.mefemasys.xicola.backend.models.Estado;

@Getter
@Setter
public class EstadoDTO {
    private Long id;
    private String descricao;

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();
        this.descricao = estado.getDescricao();
    }
    private static final Logger LOG = Logger.getLogger(EstadoDTO.class.getName());
}
