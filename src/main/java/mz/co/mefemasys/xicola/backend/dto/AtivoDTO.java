package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;

import mz.co.mefemasys.xicola.backend.models.Ativo;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.logging.Logger;

@Data
public class AtivoDTO {

    private static final Logger LOG = Logger.getLogger(AtivoDTO.class.getName());

    private Long id;

    private String descricao;

    private String tipo;

    private LocalDate dataAquisicao;

    private BigDecimal valorAquisicao;

    private String localizacao;

    private String estado;

    public AtivoDTO(Ativo ativo) {
        this.id = ativo.getId();

        this.descricao = ativo.getDescricao();

        this.tipo = ativo.getTipo();

        this.dataAquisicao = ativo.getDataAquisicao();

        this.valorAquisicao = ativo.getValorAquisicao();

        this.localizacao = ativo.getLocalizacao();

        this.estado = ativo.getEstado().getDescricao();

    }
}
