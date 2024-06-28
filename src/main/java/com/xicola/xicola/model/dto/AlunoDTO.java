package com.xicola.xicola.model.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Distrito;

import lombok.Data;

@Data

public class AlunoDTO {

    private Long id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private Distrito distritoNascimento;
    private String sexo;
    private String bilheteIdentificacao;
    private String religiao;
    private String grupoSanguineo;
    private String endereco;
    private Instant dataRegisto;
    private String estado;
    private String escolaAnterior;
    private String nomeDoPai;
    private String nomeDaMae;
    private Long numeroTelefonePrincipal;

    public AlunoDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.nomeCompleto = aluno.getNomeCompleto();
        this.dataNascimento = aluno.getDataNascimento();
        this.distritoNascimento = aluno.getDistritoNascimento();
        this.sexo = aluno.getSexo();
        this.bilheteIdentificacao = aluno.getBilheteIdentificacao();
        this.religiao = aluno.getReligiao();
        this.grupoSanguineo = aluno.getGrupoSanguineo();
        this.endereco = aluno.getEndereco();
        this.dataRegisto = aluno.getDataRegisto();
        this.estado = aluno.getEstado().getDescricao();
        this.escolaAnterior = aluno.getEscolaAnterior();
        this.nomeDoPai = aluno.getNomeDoPai();
        this.nomeDaMae = aluno.getNomeDaMae();
        this.numeroTelefonePrincipal = aluno.getNumeroTelefonePrincipal();
    }
}
