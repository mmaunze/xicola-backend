package mz.co.mefemasys.xicola.backend.dto.create;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class CreateEncarregadoDTO implements MetodosGerais {

    private String nomeCompleto;
    private String email;
    private LocalDate dataNascimento;
    private String distritoNascimento;
    private String sexo;
    private String bilheteIdentificacao;
    private String religiao;
    private String grupoSanguineo;
    private String endereco;
    private String sectorTrabalho;
    private String localTrabalho;
    private String nomeDoPai;
    private String nomeDaMae;
    private Long numeroTelefonePrincipal;
    private Long numeroTelefoneAlternativo;
}
