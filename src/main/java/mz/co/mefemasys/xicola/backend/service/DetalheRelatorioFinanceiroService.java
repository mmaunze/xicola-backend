package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.DetalheRelatorioFinanceiro;
import mz.co.mefemasys.xicola.backend.repository.DetalheRelatorioFinanceiroRepository;
import mz.co.mefemasys.xicola.backend.repository.RelatorioFinanceiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public DetalheRelatorioFinanceiro findById(Long id) {
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
    public DetalheRelatorioFinanceiro update(Long id, DetalheRelatorioFinanceiro detalheRelatorioAtualizado) {
        validarDetalheRelatorioExistente(id);
        validarDetalheRelatorio(detalheRelatorioAtualizado);

        var detalheRelatorioExistente = detalheRelatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DETALHE_RELATORIO_NOT_FOUND_MESSAGE + id));

        mapearDetalheRelatorioAtualizado(detalheRelatorioExistente, detalheRelatorioAtualizado);

        return detalheRelatorioFinanceiroRepository.save(detalheRelatorioExistente);
    }

    @Transactional
    public void delete(Long id) {
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

    private void validarRelatorioFinanceiro(Long relatorioId) {
        if (!relatorioFinanceiroRepository.existsById(relatorioId)) {
            throw new ResourceNotFoundException(RELATORIO_NOT_FOUND_MESSAGE + relatorioId);
        }
    }

    private void validarDetalheRelatorioExistente(Long id) {
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
