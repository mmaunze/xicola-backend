package com.xicola.xicola.service;

import com.xicola.xicola.model.Estado;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição do estado não pode estar vazia";

    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Estado findById(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Estado> findEstado(String estado) {
        return estadoRepository.findEstado(estado);
    }

    @Transactional
    public Estado create(Estado estado) {
        validarEstado(estado);
        return estadoRepository.save(estado);
    }

    @Transactional
    public Estado update(Integer id, Estado estadoAtualizado) {
        var estadoExistente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + id));

        validarEstado(estadoAtualizado);

        estadoExistente.setDescricao(estadoAtualizado.getDescricao());
        // Aqui você pode adicionar outras atualizações conforme necessário

        return estadoRepository.save(estadoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + id);
        }
        estadoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Estado findByDescricao(String descricao) {
        return estadoRepository.findEstado(descricao)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Estado não encontrado com a descrição: " + descricao));
    }

    private void validarEstado(Estado estado) {
        if (estado.getDescricao() == null || estado.getDescricao().isBlank()) {
            throw new BadRequestException("Descrição do estado " + DESCRICAO_VAZIA_MESSAGE);
        }
        // Adicione outras validações conforme necessário para o Estado
    }
}
