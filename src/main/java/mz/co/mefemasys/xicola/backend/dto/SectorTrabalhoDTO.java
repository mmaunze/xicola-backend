package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;

@Data
public class SectorTrabalhoDTO {
    private Long id;
    private String nome;

    public SectorTrabalhoDTO(SectorTrabalho sectorTrabalho) {
        this.id = sectorTrabalho.getId();
        this.nome = sectorTrabalho.getDescricao();
    }
}
