package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.Departamento;

import lombok.Data;

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
}
