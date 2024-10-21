package mz.co.mefemasys.xicola.backend.dto.create;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.LocalDate;

@Data
public class CreateFuncionarioDTO implements MetodosGerais {

    private String nomeCompleto;

    private LocalDate dataNascimento;

    private String distritoNascimento;

    private String sexo;

    private String endereco;

    private String email;

    private Long numeroTelefonePrincipal;

    private Long numeroTelefoneAlternativo;

    private String cargo;

    private String departamento;

    private String estadoCivil;

    private String bilheteIdentificacao;

    private String religiao;

    private String grupoSanguineo;

    private String areaFormacao;

    private String tipoFuncionario;

    private String nomeDoPai;

    private String nomeDaMae;

}
