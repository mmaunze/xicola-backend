package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Disciplina;

import java.util.logging.Logger;

@Data
public class DisciplinaDTO {

    private static final Logger LOG = Logger.getLogger(DisciplinaDTO.class.getName());

    private Long id;

    private String nome;

    public DisciplinaDTO(Disciplina disciplina) {
        this.id = disciplina.getId();

        this.nome = disciplina.getNomeDisciplina();

    }
}
