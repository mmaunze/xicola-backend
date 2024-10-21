package mz.co.mefemasys.xicola.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Logger;
import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.Contrato;

@Data
public class ContratoDTO {
    private Long id;
    private String nome;
    private String tipo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;
    private String fornecedor;
    private Long responsavel;
    private String estado;

    public ContratoDTO(Contrato contrato) {
        this.id = contrato.getId();
        this.nome = contrato.getDescricao();
        this.tipo = contrato.getTipo();
        this.dataInicio = contrato.getDataInicio();
        this.dataFim = contrato.getDataFim();
        this.valorTotal = contrato.getValorTotal();
        this.fornecedor = contrato.getFornecedor();
        this.responsavel = contrato.getResponsavel().getId();
        this.estado = contrato.getEstado().getDescricao();
    }
    private static final Logger LOG = Logger.getLogger(ContratoDTO.class.getName());
}
