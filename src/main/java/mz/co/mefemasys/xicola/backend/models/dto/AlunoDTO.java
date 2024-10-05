package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Distrito;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Data;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

@Data

public class AlunoDTO implements MetodosGerais {

    private Long id;
    private String nomeCompleto;
    private String dataNascimento;
    private String distritoNascimento;
    private String provinciaNascimento;
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
        this.dataNascimento = converterDataParaString(aluno.getDataNascimento());
        this.distritoNascimento = aluno.getDistritoNascimento().getNomeDistrito();
        this.provinciaNascimento = aluno.getDistritoNascimento().getProvincia().getNomeProvincia();
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
