package com.xicola.xicola.service;

import com.xicola.xicola.model.Documento;
import com.xicola.xicola.repository.DocumentoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private static final String DOCUMENT_NOT_FOUND_MESSAGE = "Documento não encontrado com o ID: %d";
    private static final String DOCUMENT_TITLE_REQUIRED_MESSAGE = "O título do documento é obrigatório";
    private static final String DOCUMENT_CONTENT_REQUIRED_MESSAGE = "O conteúdo do documento é obrigatório";
    private static final String DOCUMENT_DATE_REQUIRED_MESSAGE = "A data de criação do documento é obrigatória";

    private final DocumentoRepository documentoRepository;

     @Transactional(readOnly = true)
    public List<Documento> findAll() {
        return documentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Documento findById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DOCUMENT_NOT_FOUND_MESSAGE, id)));
    }

    @Transactional
    public Documento create(Documento documento) {
        validarCamposObrigatorios(documento);
        documento.setDataCriacao(new Date().toInstant()); // Define a data de criação como a data atual
        return documentoRepository.save(documento);
    }

    @Transactional
    public Documento update(Long id, Documento documentoAtualizado) {
        validarDocumentoExistente(id);
        validarCamposObrigatorios(documentoAtualizado);

        var documentoExistente = documentoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(String.format(DOCUMENT_NOT_FOUND_MESSAGE, id)));
        mapearDocumentoAtualizado(documentoExistente, documentoAtualizado);

        return documentoRepository.save(documentoExistente);
    }

    @Transactional
    public void delete(Long id) {
        validarDocumentoExistente(id);
        documentoRepository.deleteById(id);
    }

    private void validarCamposObrigatorios(Documento documento) {
        if (documento.getTitulo() == null || documento.getTitulo().isEmpty()) {
            throw new BadRequestException(DOCUMENT_TITLE_REQUIRED_MESSAGE);
        }
        if (documento.getConteudo() == null || documento.getConteudo().isEmpty()) {
            throw new BadRequestException(DOCUMENT_CONTENT_REQUIRED_MESSAGE);
        }
        if (documento.getDataCriacao() == null) {
            throw new BadRequestException(DOCUMENT_DATE_REQUIRED_MESSAGE);
        }
    }

    private void validarDocumentoExistente(Long id) {
        if (!documentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DOCUMENT_NOT_FOUND_MESSAGE, id));
        }
    }

    private void mapearDocumentoAtualizado(Documento documentoExistente, Documento documentoAtualizado) {
        documentoExistente.setTitulo(documentoAtualizado.getTitulo());
        documentoExistente.setConteudo(documentoAtualizado.getConteudo());
        documentoExistente.setEstado(documentoAtualizado.getEstado());
        documentoExistente.setAutor(documentoAtualizado.getAutor());
        documentoExistente.setTipoDocumento(documentoAtualizado.getTipoDocumento());
        
    }
}
