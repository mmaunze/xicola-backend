package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;

import java.util.logging.Logger;

@Data
public class SectorTrabalhoDTO {
    private static final Logger LOG = Logger.getLogger(SectorTrabalhoDTO.class.getName());
    private Long id;
    private String nome;

    public SectorTrabalhoDTO(SectorTrabalho sectorTrabalho) {
        this.id = sectorTrabalho.getId();
        this.nome = sectorTrabalho.getDescricao();
    }
}
