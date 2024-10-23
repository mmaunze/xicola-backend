package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.Instant;
import java.util.logging.Logger;

@Data
@RequiredArgsConstructor
public class EncarregadoEducacaoDTO implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(EncarregadoEducacaoDTO.class.getName());

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


    public EncarregadoEducacaoDTO(EncarregadoEducacao encarregadoEducacao) {
        this.id = encarregadoEducacao.getId();

        this.nomeCompleto = encarregadoEducacao.getNomeCompleto();

        this.dataNascimento = converterDataParaString(encarregadoEducacao.getDataNascimento());

        this.distritoNascimento = encarregadoEducacao.getDistritoNascimento().getNomeDistrito();

        this.provinciaNascimento = encarregadoEducacao.getDistritoNascimento().getProvincia().getNomeProvincia();

        this.sexo = encarregadoEducacao.getSexo();

        this.email = encarregadoEducacao.getEmail();

        this.bilheteIdentificacao = encarregadoEducacao.getBilheteIdentificacao();

        this.religiao = encarregadoEducacao.getReligiao();

        this.grupoSanguineo = encarregadoEducacao.getGrupoSanguineo();

        this.endereco = encarregadoEducacao.getEndereco();

        this.dataRegisto = encarregadoEducacao.getDataRegisto();

        this.estado = encarregadoEducacao.getEstado().getDescricao();

        this.localTrabalho = encarregadoEducacao.getLocalTrabalho();

        this.nomeDoPai = encarregadoEducacao.getNomeDoPai();

        this.nomeDaMae = encarregadoEducacao.getNomeDaMae();

        this.sectorTrabalho = encarregadoEducacao.getSectorTrabalho().getDescricao();

        this.numeroTelefonePrincipal = encarregadoEducacao.getNumeroTelefonePrincipal();

        this.numeroTelefoneAlternativo = encarregadoEducacao.getNumeroTelefoneAlternativo();

        this.estadoCivil = encarregadoEducacao.getEstadoCivil();

    }
}
