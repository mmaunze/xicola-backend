package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.TipoPagamento;

import java.util.logging.Logger;

@Data
public class TipoPagamentoDTO {

    private static final Logger LOG = Logger.getLogger(TipoPagamentoDTO.class.getName());

    private Long id;

    private String nome;

    public TipoPagamentoDTO(TipoPagamento tipoPagamento) {
        this.id = tipoPagamento.getId();

        this.nome = tipoPagamento.getDescricao();

    }
}
