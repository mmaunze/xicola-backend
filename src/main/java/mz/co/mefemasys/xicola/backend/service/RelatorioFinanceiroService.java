package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.RelatorioFinanceiro;
import mz.co.mefemasys.xicola.backend.repository.RelatorioFinanceiroRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RelatorioFinanceiroService {

    private static final String RELATORIO_FINANCEIRO_NOT_FOUND_MESSAGE = "Relatório financeiro não encontrado com o ID: ";

    private static final Logger LOG = Logger.getLogger(RelatorioFinanceiroService.class.getName());

    private final RelatorioFinanceiroRepository relatorioFinanceiroRepository;

    @Transactional(readOnly = true)
    public RelatorioFinanceiro findById(Long id) {
        return relatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RELATORIO_FINANCEIRO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<RelatorioFinanceiro> findAll() {
        return relatorioFinanceiroRepository.findAll();

    }

    @Transactional
    public RelatorioFinanceiro create(RelatorioFinanceiro relatorioFinanceiro) {
        validarRelatorioFinanceiro(relatorioFinanceiro);

        return relatorioFinanceiroRepository.save(relatorioFinanceiro);

    }

    @Transactional
    public RelatorioFinanceiro update(Long id, RelatorioFinanceiro relatorioFinanceiroAtualizado) {
        var relatorioFinanceiroExistente = relatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RELATORIO_FINANCEIRO_NOT_FOUND_MESSAGE + id));

        validarRelatorioFinanceiro(relatorioFinanceiroAtualizado);

        relatorioFinanceiroExistente.setAno(relatorioFinanceiroAtualizado.getAno());

        relatorioFinanceiroExistente.setMes(relatorioFinanceiroAtualizado.getMes());

        relatorioFinanceiroExistente.setDataCriacao(relatorioFinanceiroAtualizado.getDataCriacao());

        relatorioFinanceiroExistente.setEstado(relatorioFinanceiroAtualizado.getEstado());

        relatorioFinanceiroExistente.setResponsavel(relatorioFinanceiroAtualizado.getResponsavel());

        relatorioFinanceiroExistente.setTipoRelatorio(relatorioFinanceiroAtualizado.getTipoRelatorio());

        return relatorioFinanceiroRepository.save(relatorioFinanceiroExistente);

    }

    @Transactional
    public void delete(Long id) {
        var relatorioFinanceiro = relatorioFinanceiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RELATORIO_FINANCEIRO_NOT_FOUND_MESSAGE + id));

        relatorioFinanceiroRepository.delete(relatorioFinanceiro);

    }

    private void validarRelatorioFinanceiro(RelatorioFinanceiro relatorioFinanceiro) {
        if (relatorioFinanceiro.getAno() <= 0) {
            throw new BadRequestException("O ano do relatório financeiro deve ser maior que zero.");

        }

        if (relatorioFinanceiro.getDataCriacao() == null || relatorioFinanceiro.getDataCriacao().isAfter(Instant.from(LocalDate.now()))) {
            throw new BadRequestException("A data de criação do relatório não pode ser nula e deve estar no passado.");

        }

        // Adicione outras validações conforme necessário
    }
}
