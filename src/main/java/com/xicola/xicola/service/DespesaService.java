package com.xicola.xicola.service;

import com.xicola.xicola.model.Despesa;
import com.xicola.xicola.repository.CategoriaFinanceiraRepository;
import com.xicola.xicola.repository.DespesaRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.FuncionarioRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private static final String DESPESA_NOT_FOUND_MESSAGE = "Despesa não encontrada com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição da despesa não pode estar vazia";
    private static final String VALOR_INVALIDO_MESSAGE = "O valor da despesa deve ser maior que zero";
    private static final String DATA_DESPESA_VAZIA_MESSAGE = "A data da despesa não pode estar vazia";
    private static final String CATEGORIA_NOT_FOUND_MESSAGE = "Categoria financeira não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String RESPONSAVEL_NOT_FOUND_MESSAGE = "Funcionário responsável não encontrado com o ID: ";

    private final DespesaRepository despesaRepository;
    private final CategoriaFinanceiraRepository categoriaFinanceiraRepository;
    private final EstadoRepository estadoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public Despesa findById(Integer id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DESPESA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Despesa> findAll() {
        return despesaRepository.findAll();
    }

    @Transactional
    public Despesa create(Despesa despesa) {
        validarDespesa(despesa);
        return despesaRepository.save(despesa);
    }

    @Transactional
    public Despesa update(Integer id, Despesa despesaAtualizada) {
        validarDespesaExistente(id);
        validarDespesa(despesaAtualizada);

        var despesaExistente = despesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DESPESA_NOT_FOUND_MESSAGE + id));

        mapearDespesaAtualizada(despesaExistente, despesaAtualizada);

        return despesaRepository.save(despesaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        validarDespesaExistente(id);
        despesaRepository.deleteById(id);
    }

    private void validarDespesa(Despesa despesa) {
        if (despesa.getDescricao() == null || despesa.getDescricao().isEmpty()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);
        }
        if (despesa.getValor() == null || despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(VALOR_INVALIDO_MESSAGE);
        }
        if (despesa.getDataDespesa() == null) {
            throw new BadRequestException(DATA_DESPESA_VAZIA_MESSAGE);
        }
        validarCategoria(despesa.getCategoria().getId());
        validarEstado(despesa.getEstado().getId());
        validarFuncionario(despesa.getResponsavel().getId());
    }

    private void validarCategoria(Integer categoriaId) {
        if (!categoriaFinanceiraRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException(CATEGORIA_NOT_FOUND_MESSAGE + categoriaId);
        }
    }

    private void validarEstado(Integer estadoId) {
        if (!estadoRepository.existsById(estadoId)) {
            throw new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + estadoId);
        }
    }

    private void validarFuncionario(Long funcionarioId) {
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new ResourceNotFoundException(RESPONSAVEL_NOT_FOUND_MESSAGE + funcionarioId);
        }
    }

    private void validarDespesaExistente(Integer id) {
        if (!despesaRepository.existsById(id)) {
            throw new ResourceNotFoundException(DESPESA_NOT_FOUND_MESSAGE + id);
        }
    }

    private void mapearDespesaAtualizada(Despesa despesaExistente, Despesa despesaAtualizada) {
        despesaExistente.setDescricao(despesaAtualizada.getDescricao());
        despesaExistente.setValor(despesaAtualizada.getValor());
        despesaExistente.setDataDespesa(despesaAtualizada.getDataDespesa());
        despesaExistente.setCategoria(despesaAtualizada.getCategoria());
        despesaExistente.setEstado(despesaAtualizada.getEstado());
        despesaExistente.setResponsavel(despesaAtualizada.getResponsavel());
    }
}
