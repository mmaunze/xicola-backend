package mz.co.mefemasys.xicola.backend.dto.create;

import lombok.Data;

@Data
public class CreatePagamentoDTO {
    private long aluno;
    private float valor;
    private String tipoPagamento;
    private long responsavel;
    private String observacao;
    private String metodoPagamento;
}
