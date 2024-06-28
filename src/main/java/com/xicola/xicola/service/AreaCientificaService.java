package com.xicola.xicola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.AreaCientifica;
import com.xicola.xicola.repository.AreaCientificaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaCientificaService {

    private static final String AREA_CIENTIFICA_NOT_FOUND_MESSAGE = "Área científica não encontrada com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";
    private static final String DESCRICAO_CURTA_MESSAGE = "Descrição curta demais";

    private final AreaCientificaRepository areaCientificaRepository;

    @Transactional(readOnly = true)
    public AreaCientifica findById(Integer id) {
        return areaCientificaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<AreaCientifica> findAll() {
        return areaCientificaRepository.findAll();
    }

    @Transactional
    public AreaCientifica create(AreaCientifica areaCientifica) {
        validarDadosObrigatorios(areaCientifica);
        return areaCientificaRepository.save(areaCientifica);
    }

    @Transactional
    public AreaCientifica update(Integer id, AreaCientifica areaCientificaAtualizada) {
        AreaCientifica areaCientificaExistente = areaCientificaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE + id));

        validarDadosObrigatorios(areaCientificaAtualizada);

        areaCientificaExistente.setDescricao(areaCientificaAtualizada.getDescricao());

        return areaCientificaRepository.save(areaCientificaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!areaCientificaRepository.existsById(id)) {
            throw new ResourceNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE + id);
        }
        areaCientificaRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(AreaCientifica areaCientifica) {
        if (areaCientifica.getDescricao() == null || areaCientifica.getDescricao().isBlank()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);
        }
        if (areaCientifica.getDescricao().length() < 5) {
            throw new BadRequestException(DESCRICAO_CURTA_MESSAGE);
        }
    }
}
