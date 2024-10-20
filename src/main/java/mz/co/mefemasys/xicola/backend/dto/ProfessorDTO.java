package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Professor;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class ProfessorDTO implements MetodosGerais {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String dataNascimento;
    private String distritoNascimento;
    private String provinciaNascimento;
    private String sexo;
    private String bilheteIdentificacao;
    private String religiao;
    private String grupoSanguineo;
    private String endereco;
    private String estadoCivil;
    private String areaFormacao;
    private Instant dataContracto;
    private String estado;
    private String escolaAnterior;
    private String nomeDoPai;
    private String nomeDaMae;
    private Long numeroTelefonePrincipal;
    private Long numeroTelefoneAlternativo;


    public ProfessorDTO(Professor professor) {
        this.id = professor.getId();
        this.nomeCompleto = professor.getNomeCompleto();
        this.dataNascimento = converterDataParaString(professor.getDataNascimento());
        this.distritoNascimento = professor.getDistritoNascimento().getNomeDistrito();
        this.provinciaNascimento = professor.getDistritoNascimento().getProvincia().getNomeProvincia();
        this.sexo = professor.getSexo();
        this.email = professor.getEmail();
        this.bilheteIdentificacao = professor.getBilheteIdentificacao();
        this.religiao = professor.getReligiao();
        this.grupoSanguineo = professor.getGrupoSanguineo();
        this.endereco = professor.getEndereco();
        this.dataContracto = professor.getDataContracto();
        this.estado = professor.getEstado().getDescricao();
        this.escolaAnterior = professor.getEscolaAnterior();
        this.nomeDoPai = professor.getNomeDoPai();
        this.nomeDaMae = professor.getNomeDaMae();
        this.estadoCivil = professor.getEstadoCivil();
        this.areaFormacao = professor.getAreaFormacao().getDescricao();
        this.numeroTelefonePrincipal = professor.getNumeroTelefonePrincipal();
        this.numeroTelefoneAlternativo = professor.getNumeroTelefoneAlternativo();
    }
}
