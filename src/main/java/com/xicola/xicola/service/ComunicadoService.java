package com.xicola.xicola.service;

import com.xicola.xicola.model.Comunicado;
import com.xicola.xicola.repository.ComunicadoRepository;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComunicadoService {

    private static final String COMUNICADO_NOT_FOUND_MESSAGE = "Comunicado não encontrado com o ID: ";
    private static final String TITULO_VAZIO_MESSAGE = "O título do comunicado não pode estar vazio";
    private static final String CONTEUDO_VAZIO_MESSAGE = "O conteúdo do comunicado não pode estar vazio";

    private final ComunicadoRepository comunicadoRepository;

    @Transactional(readOnly = true)
    public Comunicado findById(Long id) {
        return comunicadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMUNICADO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Comunicado> findAll() {
        return comunicadoRepository.findAll();
    }

    @Transactional
    public Comunicado create(Comunicado comunicado) {
        validarDadosObrigatorios(comunicado);
        comunicado.setDataPublicacao(new Date().toInstant()); // Definindo a data de publicação como a data atual
        return comunicadoRepository.save(comunicado);
    }

    @Transactional
    public Comunicado update(Long id, Comunicado comunicadoAtualizado) {
        var comunicadoOptional = comunicadoRepository.findById(id);
        if (comunicadoOptional.isEmpty()) {
            throw new ResourceNotFoundException(COMUNICADO_NOT_FOUND_MESSAGE + id);
        }

        var comunicadoExistente = comunicadoOptional.get();
        validarDadosObrigatorios(comunicadoAtualizado);
        comunicadoExistente.setTitulo(comunicadoAtualizado.getTitulo());
        comunicadoExistente.setConteudo(comunicadoAtualizado.getConteudo());

        return comunicadoRepository.save(comunicadoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!comunicadoRepository.existsById(id)) {
            throw new ResourceNotFoundException(COMUNICADO_NOT_FOUND_MESSAGE + id);
        }

        comunicadoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Comunicado comunicado) {
        if (comunicado.getTitulo() == null || comunicado.getTitulo().isBlank()) {
            throw new ResourceNotFoundException(TITULO_VAZIO_MESSAGE);
        }
        if (comunicado.getConteudo() == null || comunicado.getConteudo().isBlank()) {
            throw new ResourceNotFoundException(CONTEUDO_VAZIO_MESSAGE);
        }
    }
}
