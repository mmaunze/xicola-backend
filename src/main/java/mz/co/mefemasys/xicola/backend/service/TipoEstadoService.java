package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.TipoEstado;
import mz.co.mefemasys.xicola.backend.repository.TipoEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEstadoService {

    private static final String TIPO_ESTADO_NOT_FOUND_MESSAGE = "Tipo de estado não encontrado com o ID: ";

    private final TipoEstadoRepository tipoEstadoRepository;

    @Transactional(readOnly = true)
    public TipoEstado findById(Long id) {
        return tipoEstadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_ESTADO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoEstado> findAll() {
        return tipoEstadoRepository.findAll();
    }

    @Transactional
    public TipoEstado create(TipoEstado tipoEstado) {
        validarTipoEstado(tipoEstado);

        return tipoEstadoRepository.save(tipoEstado);
    }

    @Transactional
    public TipoEstado update(Long id, TipoEstado tipoEstadoAtualizado) {
        var tipoEstadoExistente = tipoEstadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_ESTADO_NOT_FOUND_MESSAGE + id));

        validarTipoEstado(tipoEstadoAtualizado);

        tipoEstadoExistente.setDescricao(tipoEstadoAtualizado.getDescricao());

        return tipoEstadoRepository.save(tipoEstadoExistente);
    }

    @Transactional
    public void delete(Long id) {
        var tipoEstado = tipoEstadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_ESTADO_NOT_FOUND_MESSAGE + id));

        tipoEstadoRepository.delete(tipoEstado);
    }

    private void validarTipoEstado(TipoEstado tipoEstado) {
        if (tipoEstado.getDescricao() == null || tipoEstado.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de estado não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
