package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Departamento;

import lombok.Data;

@Data
public class DepartamentoDTO {
    private Integer id;
    private String nome;
    private String sigla;

    public DepartamentoDTO(Departamento departamento) {
        this.id = departamento.getId();
        this.nome = departamento.getDescricao();
        this.sigla = departamento.getSigla();
    }
}
