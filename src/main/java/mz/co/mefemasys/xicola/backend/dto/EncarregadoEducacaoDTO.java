package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class EncarregadoEducacaoDTO implements MetodosGerais {

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
    private String sectorTrabalho;
    private Instant dataRegisto;
    private String estado;
    private String localTrabalho;
    private String nomeDoPai;
    private String nomeDaMae;
    private Long numeroTelefonePrincipal;
    private Long numeroTelefoneAlternativo;


    public EncarregadoEducacaoDTO(EncarregadoEducacao professor) {
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
        this.dataRegisto = professor.getDataRegisto();
        this.estado = professor.getEstado().getDescricao();
        this.localTrabalho = professor.getLocalTrabalho();
        this.nomeDoPai = professor.getNomeDoPai();
        this.nomeDaMae = professor.getNomeDaMae();
        this.sectorTrabalho = professor.getSectorTrabalho().getDescricao();
        this.numeroTelefonePrincipal = professor.getNumeroTelefonePrincipal();
        this.numeroTelefoneAlternativo = professor.getNumeroTelefoneAlternativo();
    }
}
