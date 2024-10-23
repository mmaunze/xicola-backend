package mz.co.mefemasys.xicola.backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";

    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição do estado não pode estar vazia";

    private static final Logger LOG = Logger.getLogger(EstadoService.class.getName());

    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Estado findById(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return estadoRepository.findAll();

    }

    @Transactional(readOnly = true)
    public List<Estado> findEstadoTipo(String tipo) {
        return estadoRepository.findByTipoEstado(tipo);

    }

    @Transactional(readOnly = true)
    public Estado findEstado(String estado) {
        return estadoRepository.findEstado(estado)
                .orElseThrow(() -> new EntityNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

    }

    @Transactional
    public Estado create(Estado estado) {
        validarEstado(estado);

        return estadoRepository.save(estado);

    }

    @Transactional
    public Estado update(Long id, Estado estadoAtualizado) {
        var estadoExistente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + id));

        validarEstado(estadoAtualizado);

        estadoExistente.setDescricao(estadoAtualizado.getDescricao());

        // Aqui você pode adicionar outras atualizações conforme necessário
        return estadoRepository.save(estadoExistente);

    }

    @Transactional
    public void delete(Long id) {
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
