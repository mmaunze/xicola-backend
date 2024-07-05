package com.xicola.xicola.model.dto;

import java.time.LocalDate;

import com.xicola.xicola.model.EncarregadoEducacao;

import lombok.Data;

@Data
public class EncarregadoEducacaoDTO {
    private Long id;
    private Long utilizador;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String distritoNascimento;
    private String sexo;
    private String religiao;
    private String localTrabalho;
    private String sectorTrabalho;
    private String endereco;
    private String email;
    private String grupoSanguineo;
    private Long numeroTelefonePrincipal;
    private Long numeroTelefoneAlternativo;
    private String estado;

    public EncarregadoEducacaoDTO(EncarregadoEducacao encarregadoEducacao) {
        this.id = encarregadoEducacao.getId();
        this.utilizador = encarregadoEducacao.getUtilizador().getId();
        this.nomeCompleto = encarregadoEducacao.getNomeCompleto();
        this.dataNascimento = encarregadoEducacao.getDataNascimento();
        this.distritoNascimento = encarregadoEducacao.getDistritoNascimento() != null
                ? encarregadoEducacao.getDistritoNascimento().getNomeDistrito()
                : null;
        this.sexo = encarregadoEducacao.getSexo();
        this.religiao = encarregadoEducacao.getReligiao();
        this.localTrabalho = encarregadoEducacao.getLocalTrabalho();
        this.sectorTrabalho = encarregadoEducacao.getSectorTrabalho().getDescricao();
        this.endereco = encarregadoEducacao.getEndereco();
        this.email = encarregadoEducacao.getEmail();
        this.grupoSanguineo = encarregadoEducacao.getGrupoSanguineo();
        this.numeroTelefonePrincipal = encarregadoEducacao.getNumeroTelefonePrincipal();
        this.numeroTelefoneAlternativo = encarregadoEducacao.getNumeroTelefoneAlternativo();
        this.estado = encarregadoEducacao.getEstado().getDescricao();
    }
}
