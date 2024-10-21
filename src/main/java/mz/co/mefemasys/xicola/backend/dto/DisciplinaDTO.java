package mz.co.mefemasys.xicola.backend.dto;

import java.util.logging.Logger;
import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Disciplina;

@Data
public class DisciplinaDTO {
    private Long id;
    private String nome;

    public DisciplinaDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNomeDisciplina();
    }
    private static final Logger LOG = Logger.getLogger(DisciplinaDTO.class.getName());
}
