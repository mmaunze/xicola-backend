package com.xicola.xicola.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.DetalheRelatorioFinanceiro;
import com.xicola.xicola.repository.DetalheRelatorioFinanceiroRepository;
import com.xicola.xicola.repository.RelatorioFinanceiroRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalheRelatorioFinanceiroService {

    private static final String DETALHE_RELATORIO_NOT_FOUND_MESSAGE = "Detalhe do relatório financeiro não encontrado com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição do detalhe do relatório não pode estar vazia";
    private static final String VALOR_INVALIDO_MESSAGE = "O valor do detalhe do relatório deve ser maior que zero";
    private static final String RELATORIO_NOT_FOUND_MESSAGE = "Relatório financeiro não encontrado com o ID: ";

    private final DetalheRelatorioFinanceiroRepository detalheRelatorioFinanceiroRepository;
    private final RelatorioFinanceiroRepository relatorioFinanceiroRepository;

    @Transactional(readOnly = true)
    public DetalheRelatorioFinanceiro findById(Integer id) {
        return detalheRelatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DETALHE_RELATORIO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<DetalheRelatorioFinanceiro> findAll() {
        return detalheRelatorioFinanceiroRepository.findAll();
    }

    @Transactional
    public DetalheRelatorioFinanceiro create(DetalheRelatorioFinanceiro detalheRelatorio) {
        validarDetalheRelatorio(detalheRelatorio);
        return detalheRelatorioFinanceiroRepository.save(detalheRelatorio);
    }

    @Transactional
    public DetalheRelatorioFinanceiro update(Integer id, DetalheRelatorioFinanceiro detalheRelatorioAtualizado) {
        validarDetalheRelatorioExistente(id);
        validarDetalheRelatorio(detalheRelatorioAtualizado);

        DetalheRelatorioFinanceiro detalheRelatorioExistente = detalheRelatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DETALHE_RELATORIO_NOT_FOUND_MESSAGE + id));

        mapearDetalheRelatorioAtualizado(detalheRelatorioExistente, detalheRelatorioAtualizado);

        return detalheRelatorioFinanceiroRepository.save(detalheRelatorioExistente);
    }

    @Transactional
    public void delete(Integer id) {
        validarDetalheRelatorioExistente(id);
        detalheRelatorioFinanceiroRepository.deleteById(id);
    }

    private void validarDetalheRelatorio(DetalheRelatorioFinanceiro detalheRelatorio) {
        if (detalheRelatorio.getDescricao() == null || detalheRelatorio.getDescricao().isEmpty()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);
        }
        if (detalheRelatorio.getValor() == null || detalheRelatorio.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(VALOR_INVALIDO_MESSAGE);
        }
        validarRelatorioFinanceiro(detalheRelatorio.getRelatorio().getId());
    }

    private void validarRelatorioFinanceiro(Integer relatorioId) {
        if (!relatorioFinanceiroRepository.existsById(relatorioId)) {
            throw new ResourceNotFoundException(RELATORIO_NOT_FOUND_MESSAGE + relatorioId);
        }
    }

    private void validarDetalheRelatorioExistente(Integer id) {
        if (!detalheRelatorioFinanceiroRepository.existsById(id)) {
            throw new ResourceNotFoundException(DETALHE_RELATORIO_NOT_FOUND_MESSAGE + id);
        }
    }

    private void mapearDetalheRelatorioAtualizado(DetalheRelatorioFinanceiro detalheRelatorioExistente,
            DetalheRelatorioFinanceiro detalheRelatorioAtualizado) {
        detalheRelatorioExistente.setDescricao(detalheRelatorioAtualizado.getDescricao());
        detalheRelatorioExistente.setValor(detalheRelatorioAtualizado.getValor());
        detalheRelatorioExistente.setRelatorio(detalheRelatorioAtualizado.getRelatorio());
    }
}
