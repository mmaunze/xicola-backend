package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoRelatorio;
import com.xicola.xicola.repository.TipoRelatorioRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRelatorioService {

    private static final String TIPO_RELATORIO_NOT_FOUND_MESSAGE = "Tipo de relatório não encontrado com o ID: ";

    private final TipoRelatorioRepository tipoRelatorioRepository;

    @Transactional(readOnly = true)
    public TipoRelatorio findById(Long id) {
        return tipoRelatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_RELATORIO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoRelatorio> findAll() {
        return tipoRelatorioRepository.findAll();
    }

    @Transactional
    public TipoRelatorio create(TipoRelatorio tipoRelatorio) {
        validarTipoRelatorio(tipoRelatorio);

        return tipoRelatorioRepository.save(tipoRelatorio);
    }

    @Transactional
    public TipoRelatorio update(Long id, TipoRelatorio tipoRelatorioAtualizado) {
        TipoRelatorio tipoRelatorioExistente = tipoRelatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_RELATORIO_NOT_FOUND_MESSAGE + id));

        validarTipoRelatorio(tipoRelatorioAtualizado);

        tipoRelatorioExistente.setDescricao(tipoRelatorioAtualizado.getDescricao());

        return tipoRelatorioRepository.save(tipoRelatorioExistente);
    }

    @Transactional
    public void delete(Long id) {
        TipoRelatorio tipoRelatorio = tipoRelatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_RELATORIO_NOT_FOUND_MESSAGE + id));

        tipoRelatorioRepository.delete(tipoRelatorio);
    }

    private void validarTipoRelatorio(TipoRelatorio tipoRelatorio) {
        if (tipoRelatorio.getDescricao() == null || tipoRelatorio.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de relatório não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
