package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.AvaliacaoAluno;

import java.io.Serializable;
import java.time.Instant;
import java.util.logging.Logger;

@Data
public class AvaliacaoAlunoDTO implements Serializable {

    private static final Logger LOG = Logger.getLogger(AvaliacaoAlunoDTO.class.getName());

    private Long id;

    private Long aluno;

    private Long avaliacao;

    private Integer trimestre;

    private Integer anoLectivo;

    private Instant dataLancamento;

    private Double nota;

    private String observacao;

    private String estado;

    public AvaliacaoAlunoDTO() {
    }

    public AvaliacaoAlunoDTO(AvaliacaoAluno avaliacaoAluno) {
        this.id = avaliacaoAluno.getId();

        this.aluno = avaliacaoAluno.getAluno().getId();

        this.trimestre = avaliacaoAluno.getTrimestre();

        this.anoLectivo = avaliacaoAluno.getAnoLectivo();

        this.dataLancamento = avaliacaoAluno.getDataLancamento();

        this.nota = avaliacaoAluno.getNota();

        this.observacao = avaliacaoAluno.getObservacao();

        this.avaliacao = avaliacaoAluno.getAvaliacao().getId();

        this.estado = avaliacaoAluno.getEstado().getDescricao();

    }
}
