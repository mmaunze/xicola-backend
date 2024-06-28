package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoDocumento;
import com.xicola.xicola.repository.TipoDocumentoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {

    private static final String TIPO_DOCUMENTO_NOT_FOUND_MESSAGE = "Tipo de documento não encontrado com o ID: ";

    private final TipoDocumentoRepository tipoDocumentoRepository;

    @Transactional(readOnly = true)
    public TipoDocumento findById(Long id) {
        return tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_DOCUMENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoDocumento> findAll() {
        return tipoDocumentoRepository.findAll();
    }

    @Transactional
    public TipoDocumento create(TipoDocumento tipoDocumento) {
        validarTipoDocumento(tipoDocumento);

        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Transactional
    public TipoDocumento update(Long id, TipoDocumento tipoDocumentoAtualizado) {
        TipoDocumento tipoDocumentoExistente = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_DOCUMENTO_NOT_FOUND_MESSAGE + id));

        validarTipoDocumento(tipoDocumentoAtualizado);

        tipoDocumentoExistente.setDescricao(tipoDocumentoAtualizado.getDescricao());

        return tipoDocumentoRepository.save(tipoDocumentoExistente);
    }

    @Transactional
    public void delete(Long id) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_DOCUMENTO_NOT_FOUND_MESSAGE + id));

        tipoDocumentoRepository.delete(tipoDocumento);
    }

    private void validarTipoDocumento(TipoDocumento tipoDocumento) {
        if (tipoDocumento.getDescricao() == null || tipoDocumento.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de documento não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
