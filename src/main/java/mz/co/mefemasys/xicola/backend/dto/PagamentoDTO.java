package mz.co.mefemasys.xicola.backend.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Pagamento;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@RequiredArgsConstructor
public class PagamentoDTO {

    private long id;
    private String referencia;
    private String aluno;
    private long id_aluno;
    private BigDecimal valor;
    private String tipoPagamento;
    private Instant dataPagamento;
    private String responsavel;
    private long id_responsavel;
    private String observacao;
    private String estado;
    private String metodoPagamento;

    public PagamentoDTO(Pagamento pagamento){
        this.id = pagamento.getId();
        this.referencia = pagamento.getReferencia();
        this.aluno = pagamento.getAluno().getNomeCompleto();
        this.valor = pagamento.getValor();
        this.tipoPagamento = pagamento.getTipoPagamento().getDescricao();
        this.dataPagamento = pagamento.getDataPagamento();
        this.responsavel = pagamento.getResponsavel().getNomeCompleto();
        this.id_aluno = pagamento.getAluno().getId();
        this.id_responsavel = pagamento.getResponsavel().getId();
        this.estado = pagamento.getEstado().getDescricao();
        this.observacao = pagamento.getObservacao();
        this.metodoPagamento = pagamento.getMetodoPagamento().getDescricao();

    }

}
