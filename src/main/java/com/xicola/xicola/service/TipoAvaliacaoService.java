package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoAvaliacao;
import com.xicola.xicola.repository.TipoAvaliacaoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoAvaliacaoService {

    private static final String TIPO_AVALIACAO_NOT_FOUND_MESSAGE = "Tipo de avaliação não encontrado com o ID: ";

    private final TipoAvaliacaoRepository tipoAvaliacaoRepository;

    @Transactional(readOnly = true)
    public TipoAvaliacao findById(Integer id) {
        return tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoAvaliacao> findAll() {
        return tipoAvaliacaoRepository.findAll();
    }

    @Transactional
    public TipoAvaliacao create(TipoAvaliacao tipoAvaliacao) {
        validarTipoAvaliacao(tipoAvaliacao);

        return tipoAvaliacaoRepository.save(tipoAvaliacao);
    }

    @Transactional
    public TipoAvaliacao update(Integer id, TipoAvaliacao tipoAvaliacaoAtualizado) {
        TipoAvaliacao tipoAvaliacaoExistente = tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));

        validarTipoAvaliacao(tipoAvaliacaoAtualizado);

        tipoAvaliacaoExistente.setDescricao(tipoAvaliacaoAtualizado.getDescricao());

        return tipoAvaliacaoRepository.save(tipoAvaliacaoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        TipoAvaliacao tipoAvaliacao = tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));

        tipoAvaliacaoRepository.delete(tipoAvaliacao);
    }

    private void validarTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        if (tipoAvaliacao.getDescricao() == null || tipoAvaliacao.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de avaliação não pode ser nula ou vazia.");
        }


    }
}
