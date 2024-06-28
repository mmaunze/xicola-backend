package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoPagamento;
import com.xicola.xicola.repository.TipoPagamentoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPagamentoService {

    private static final String TIPO_PAGAMENTO_NOT_FOUND_MESSAGE = "Tipo de pagamento não encontrado com o ID: ";

    private final TipoPagamentoRepository tipoPagamentoRepository;

    @Transactional(readOnly = true)
    public TipoPagamento findById(Integer id) {
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
    public TipoPagamento update(Integer id, TipoPagamento tipoPagamentoAtualizado) {
        TipoPagamento tipoPagamentoExistente = tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        validarTipoPagamento(tipoPagamentoAtualizado);

        tipoPagamentoExistente.setDescricao(tipoPagamentoAtualizado.getDescricao());

        return tipoPagamentoRepository.save(tipoPagamentoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        TipoPagamento tipoPagamento = tipoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        tipoPagamentoRepository.delete(tipoPagamento);
    }

    private void validarTipoPagamento(TipoPagamento tipoPagamento) {
        if (tipoPagamento.getDescricao() == null || tipoPagamento.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de pagamento não pode ser nula ou vazia.");
        }

    }
}
