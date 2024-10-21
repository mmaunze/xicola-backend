package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Departamento;

import java.util.logging.Logger;

@Data
public class DepartamentoDTO {

    private static final Logger LOG = Logger.getLogger(DepartamentoDTO.class.getName());

    private Long id;

    private String nome;

    private String sigla;

    public DepartamentoDTO(Departamento departamento) {
        this.id = departamento.getId();

        this.nome = departamento.getDescricao();

        this.sigla = departamento.getSigla();

    }
}
