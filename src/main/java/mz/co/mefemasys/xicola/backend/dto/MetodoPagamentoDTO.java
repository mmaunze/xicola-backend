package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.MetodoPagamento;

import java.util.logging.Logger;

@Data
public class MetodoPagamentoDTO {

    private static final Logger LOG = Logger.getLogger(MetodoPagamentoDTO.class.getName());

    private Long id;

    private String nome;

    public MetodoPagamentoDTO(MetodoPagamento metodoPagamento) {
        this.id = metodoPagamento.getId();

        this.nome = metodoPagamento.getDescricao();

    }
}
