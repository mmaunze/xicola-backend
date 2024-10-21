package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Funcionario;

import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import java.time.Instant;

@Data
public class FuncionarioDTO implements MetodosGerais {

    private Long id;

    private String nomeCompleto;

    private String dataNascimento;

    private String distritoNascimento;

    private String sexo;

    private String endereco;

    private String email;

    private Long numeroTelefonePrincipal;

    private Long numeroTelefoneAlternativo;

    private Instant dataContracto;

    private String cargo;

    private String departamento;

    private String estado;

    private String estadoCivil;

    private String bilheteIdentificacao;

    private String religiao;

    private String grupoSanguineo;

    private String areaFormacao;

    private String tipoFuncionario;

    private String nomeDoPai;

    private String nomeDaMae;

    public FuncionarioDTO(Funcionario funcionario) {
        this.id = funcionario.getId();

        this.nomeCompleto = funcionario.getNomeCompleto();

        this.dataNascimento = converterDataParaString(funcionario.getDataNascimento());

        this.distritoNascimento = funcionario.getDistritoNascimento().getNomeDistrito();

        this.sexo = funcionario.getSexo();

        this.endereco = funcionario.getEndereco();

        this.email = funcionario.getEmail();

        this.numeroTelefonePrincipal = funcionario.getNumeroTelefonePrincipal();

        this.numeroTelefoneAlternativo = funcionario.getNumeroTelefoneAlternativo();

        this.dataContracto = funcionario.getDataContracto();

        this.cargo = funcionario.getCargo().getDescricao();

        this.departamento = funcionario.getDepartamento().getDescricao();

        this.estado = funcionario.getEstado().getDescricao();

        this.estadoCivil = funcionario.getEstadoCivil();

        this.bilheteIdentificacao = funcionario.getBilheteIdentificacao();

        this.religiao = funcionario.getReligiao();

        this.grupoSanguineo = funcionario.getGrupoSanguineo();

        this.areaFormacao = funcionario.getAreaFormacao().getDescricao();

        this.tipoFuncionario = funcionario.getTipoFuncionario().getDescricao();

        this.nomeDaMae = funcionario.getNomeDaMae();

        this.nomeDoPai = funcionario.getNomeDoPai();

    }

}
