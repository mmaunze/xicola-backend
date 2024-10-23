package mz.co.mefemasys.xicola.backend.dto.create;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.logging.Logger;

@Data
public class CreateAlunoDTO {

    private static final Logger LOG = Logger.getLogger(CreateAlunoDTO.class.getName());

    private String nomeCompleto;

    private LocalDate dataNascimento;

    private String distritoNascimento;

    private String sexo;

    private String bilheteIdentificacao;

    private String religiao;

    private String grupoSanguineo;

    private String endereco;

    private String escolaAnterior;

    private String nomeDoPai;

    private String nomeDaMae;

    private Long numeroTelefonePrincipal;

}
