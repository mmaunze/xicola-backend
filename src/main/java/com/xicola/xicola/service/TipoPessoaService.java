package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoPessoa;
import com.xicola.xicola.repository.TipoPessoaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPessoaService {

    private static final String TIPO_PESSOA_NOT_FOUND_MESSAGE = "Tipo de pessoa não encontrado com o ID: ";

    private final TipoPessoaRepository tipoPessoaRepository;

    @Transactional(readOnly = true)
    public TipoPessoa findById(Long id) {
        return tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoPessoa> findAll() {
        return tipoPessoaRepository.findAll();
    }

    @Transactional
    public TipoPessoa create(TipoPessoa tipoPessoa) {
        validarTipoPessoa(tipoPessoa);

        return tipoPessoaRepository.save(tipoPessoa);
    }

    @Transactional
    public TipoPessoa update(Long id, TipoPessoa tipoPessoaAtualizado) {
        TipoPessoa tipoPessoaExistente = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));

        validarTipoPessoa(tipoPessoaAtualizado);

        tipoPessoaExistente.setDescricao(tipoPessoaAtualizado.getDescricao());

        return tipoPessoaRepository.save(tipoPessoaExistente);
    }

    @Transactional
    public void delete(Long id) {
        TipoPessoa tipoPessoa = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));

        tipoPessoaRepository.delete(tipoPessoa);
    }

    private void validarTipoPessoa(TipoPessoa tipoPessoa) {
        if (tipoPessoa.getDescricao() == null || tipoPessoa.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de pessoa não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
