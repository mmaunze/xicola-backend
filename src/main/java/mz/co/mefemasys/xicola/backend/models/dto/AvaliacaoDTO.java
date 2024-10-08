package mz.co.mefemasys.xicola.backend.models.dto;

import java.io.Serializable;

import mz.co.mefemasys.xicola.backend.models.Avaliacao;

import lombok.Data;

@Data
public class AvaliacaoDTO implements Serializable {
    private Long id;
    private Long aluno;
    private String tipo;
    private Integer trimestre;
    private String disciplina;
    private String observacao;
    private String estado;

    public AvaliacaoDTO() {
    }

    public AvaliacaoDTO(Avaliacao avaliacao) {
        this.id = avaliacao.getId();
        this.tipo = avaliacao.getTipoAvaliacao().getDescricao();
        this.trimestre = avaliacao.getTrimestre();
        this.disciplina = avaliacao.getDisciplina().getNomeDisciplina();
        this.observacao = avaliacao.getObservacao();
        this.estado = avaliacao.getEstado().getDescricao();
    }
}
