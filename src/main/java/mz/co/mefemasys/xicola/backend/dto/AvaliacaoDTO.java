package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Avaliacao;

import java.io.Serializable;
import java.util.logging.Logger;

@Data
public class AvaliacaoDTO implements Serializable {

    private static final Logger LOG = Logger.getLogger(AvaliacaoDTO.class.getName());

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
