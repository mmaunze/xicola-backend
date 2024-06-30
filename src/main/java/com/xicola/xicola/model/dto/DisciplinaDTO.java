package com.xicola.xicola.model.dto;

import com.xicola.xicola.model.Disciplina;

import lombok.Data;

@Data
public class DisciplinaDTO {
    private Integer id;
    private String nome;

    public DisciplinaDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNomeDisciplina();
    }
}
