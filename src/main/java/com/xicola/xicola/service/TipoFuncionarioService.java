package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoFuncionario;
import com.xicola.xicola.repository.TipoFuncionarioRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoFuncionarioService {

    private static final String TIPO_FUNCIONARIO_NOT_FOUND_MESSAGE = "Tipo de funcionário não encontrado com o ID: ";

    private final TipoFuncionarioRepository tipoFuncionarioRepository;

    @Transactional(readOnly = true)
    public TipoFuncionario findById(Long id) {
        return tipoFuncionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_FUNCIONARIO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoFuncionario> findAll() {
        return tipoFuncionarioRepository.findAll();
    }

    @Transactional
    public TipoFuncionario create(TipoFuncionario tipoFuncionario) {
        validarTipoFuncionario(tipoFuncionario);

        return tipoFuncionarioRepository.save(tipoFuncionario);
    }

    @Transactional
    public TipoFuncionario update(Long id, TipoFuncionario tipoFuncionarioAtualizado) {
        var tipoFuncionarioExistente = tipoFuncionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_FUNCIONARIO_NOT_FOUND_MESSAGE + id));

        validarTipoFuncionario(tipoFuncionarioAtualizado);

        tipoFuncionarioExistente.setDescricao(tipoFuncionarioAtualizado.getDescricao());

        return tipoFuncionarioRepository.save(tipoFuncionarioExistente);
    }

    @Transactional
    public void delete(Long id) {
        var tipoFuncionario = tipoFuncionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_FUNCIONARIO_NOT_FOUND_MESSAGE + id));

        tipoFuncionarioRepository.delete(tipoFuncionario);
    }

    private void validarTipoFuncionario(TipoFuncionario tipoFuncionario) {
        if (tipoFuncionario.getDescricao() == null || tipoFuncionario.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de funcionário não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
