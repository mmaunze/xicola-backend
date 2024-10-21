package mz.co.mefemasys.xicola.backend.service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.AreaCientifica;

import mz.co.mefemasys.xicola.backend.repository.AreaCientificaRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AreaCientificaService {

    private static final String AREA_CIENTIFICA_NOT_FOUND_MESSAGE = "Área científica não encontrada com o ID: ";

    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";

    private static final String DESCRICAO_CURTA_MESSAGE = "Descrição curta demais";

    private static final Logger LOG = Logger.getLogger(AreaCientificaService.class.getName());

    private final AreaCientificaRepository areaCientificaRepository;

    @Transactional(readOnly = true)
    public AreaCientifica findById(Long id) {
        return areaCientificaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<AreaCientifica> findAll() {
        return areaCientificaRepository.findAll();

    }

    @Transactional(readOnly = true)
    public AreaCientifica findArea(String estado) {
        return areaCientificaRepository.findArea(estado)
                .orElseThrow(() -> new EntityNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE));

    }

    @Transactional
    public AreaCientifica create(AreaCientifica areaCientifica) {
        validarDadosObrigatorios(areaCientifica);

        return areaCientificaRepository.save(areaCientifica);

    }

    @Transactional
    public AreaCientifica update(Long id, AreaCientifica areaCientificaAtualizada) {
        var areaCientificaExistente = areaCientificaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_CIENTIFICA_NOT_FOUND_MESSAGE + id));

        validarDadosObrigatorios(areaCientificaAtualizada);

        areaCientificaExistente.setDescricao(areaCientificaAtualizada.getDescricao());

        return areaCientificaRepository.save(areaCientificaExistente);

    }

    @Transactional
    public void delete(Long id) {
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
