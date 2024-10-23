package mz.co.mefemasys.xicola.backend.dto.create;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.LocalDate;
import java.util.logging.Logger;

@Data
public class CreateEncarregadoDTO implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(CreateEncarregadoDTO.class.getName());

    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String sexo;
    private String bilheteIdentificacao;
    private String religiao;
    private String grupoSanguineo;
    private String endereco;
    private String estadoCivil;
    private String localTrabalho;
    private String nomeDoPai;
    private String nomeDaMae;
    private Long numeroTelefonePrincipal;
    private Long numeroTelefoneAlternativo;
    private String email;
    private String distritoNascimento;
    private String sectorTrabalho;


}
