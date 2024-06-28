package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoTransacao;
import com.xicola.xicola.repository.TipoTransacaoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoTransacaoService {

    private static final String TIPO_TRANSACAO_NOT_FOUND_MESSAGE = "Tipo de transação não encontrado com o ID: ";

    private final TipoTransacaoRepository tipoTransacaoRepository;

    @Transactional(readOnly = true)
    public TipoTransacao findById(Long id) {
        return tipoTransacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_TRANSACAO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoTransacao> findAll() {
        return tipoTransacaoRepository.findAll();
    }

    @Transactional
    public TipoTransacao create(TipoTransacao tipoTransacao) {
        validarTipoTransacao(tipoTransacao);

        return tipoTransacaoRepository.save(tipoTransacao);
    }

    @Transactional
    public TipoTransacao update(Long id, TipoTransacao tipoTransacaoAtualizado) {
        var tipoTransacaoExistente = tipoTransacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_TRANSACAO_NOT_FOUND_MESSAGE + id));

        validarTipoTransacao(tipoTransacaoAtualizado);

        tipoTransacaoExistente.setDescricao(tipoTransacaoAtualizado.getDescricao());

        return tipoTransacaoRepository.save(tipoTransacaoExistente);
    }

    @Transactional
    public void delete(Long id) {
        var tipoTransacao = tipoTransacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_TRANSACAO_NOT_FOUND_MESSAGE + id));

        tipoTransacaoRepository.delete(tipoTransacao);
    }

    private void validarTipoTransacao(TipoTransacao tipoTransacao) {
        if (tipoTransacao.getDescricao() == null || tipoTransacao.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de transação não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
