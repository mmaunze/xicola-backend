package mz.co.mefemasys.xicola.backend.dto;

import java.util.logging.Logger;
import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Departamento;

@Data
public class DepartamentoDTO {
    private Long id;
    private String nome;
    private String sigla;

    public DepartamentoDTO(Departamento departamento) {
        this.id = departamento.getId();
        this.nome = departamento.getDescricao();
        this.sigla = departamento.getSigla();
    }
    private static final Logger LOG = Logger.getLogger(DepartamentoDTO.class.getName());
}
