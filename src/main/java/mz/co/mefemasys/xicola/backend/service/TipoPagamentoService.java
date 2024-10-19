package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.TipoPagamento;
import mz.co.mefemasys.xicola.backend.repository.TipoPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPagamentoService {

    private static final String TIPO_PAGAMENTO_NOT_FOUND_MESSAGE = "Tipo de pagamento não encontrado com o ID: ";

    private final TipoPagamentoRepository tipoPagamentoRepository;

    @Transactional(readOnly = true)
    public TipoPagamento findById(Long id) {
        return tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoPagamento> findAll() {
        return tipoPagamentoRepository.findAll();
    }

    @Transactional
    public TipoPagamento create(TipoPagamento tipoPagamento) {
        validarTipoPagamento(tipoPagamento);

        return tipoPagamentoRepository.save(tipoPagamento);
    }

    @Transactional
    public TipoPagamento update(Long id, TipoPagamento tipoPagamentoAtualizado) {
        var tipoPagamentoExistente = tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        validarTipoPagamento(tipoPagamentoAtualizado);

        tipoPagamentoExistente.setDescricao(tipoPagamentoAtualizado.getDescricao());

        return tipoPagamentoRepository.save(tipoPagamentoExistente);
    }

    @Transactional
    public void delete(Long id) {
        var tipoPagamento = tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        tipoPagamentoRepository.delete(tipoPagamento);
    }

    private void validarTipoPagamento(TipoPagamento tipoPagamento) {
        if (tipoPagamento.getDescricao() == null || tipoPagamento.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de pagamento não pode ser nula ou vazia.");
        }

    }
}
