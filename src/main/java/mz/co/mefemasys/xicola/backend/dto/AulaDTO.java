package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Aula;

import java.time.Instant;

import java.util.logging.Logger;

@Data
public class AulaDTO {

    private static final Logger LOG = Logger.getLogger(AulaDTO.class.getName());

    private Long id;

    private String disciplina;

    private String titulo;

    private Integer anoLectivo;

    private Integer classe;

    private String resumo;

    private Instant dataAula;

    private String conteudo;

    private String estado;

    public AulaDTO(Aula aula) {
        this.id = aula.getId();

        this.disciplina = aula.getDisciplina().getNomeDisciplina();

        this.titulo = aula.getTitulo();

        this.anoLectivo = aula.getAnoLectivo();

        this.classe = aula.getClasse();

        this.resumo = aula.getResumo();

        this.dataAula = aula.getDataAula();

        this.conteudo = aula.getConteudo();

        this.estado = aula.getEstado().getDescricao();

    }
}
