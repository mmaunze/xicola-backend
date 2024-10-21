package mz.co.mefemasys.xicola.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.models.Orcamento;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.FuncionarioRepository;
import mz.co.mefemasys.xicola.backend.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrcamentoService {

    private static final String ORCAMENTO_NOT_FOUND_MESSAGE = "Orçamento não encontrado com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionário não encontrado com o ID: ";

    private final OrcamentoRepository orcamentoRepository;
    private final EstadoRepository estadoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public Orcamento findById(Long id) {
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ORCAMENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    @Transactional
    public Orcamento create(Orcamento orcamento) {
        validarOrcamento(orcamento);

        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public Orcamento update(Long id, Orcamento orcamentoAtualizado) {
        var orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ORCAMENTO_NOT_FOUND_MESSAGE + id));

        validarOrcamento(orcamentoAtualizado);

        orcamentoExistente.setAno(orcamentoAtualizado.getAno());
        orcamentoExistente.setValorTotal(orcamentoAtualizado.getValorTotal());
        orcamentoExistente.setDataCriacao(orcamentoAtualizado.getDataCriacao());
        orcamentoExistente.setEstado(orcamentoAtualizado.getEstado());
        orcamentoExistente.setResponsavel(orcamentoAtualizado.getResponsavel());

        return orcamentoRepository.save(orcamentoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ORCAMENTO_NOT_FOUND_MESSAGE + id);
        }
        orcamentoRepository.deleteById(id);
    }

    private void validarOrcamento(Orcamento orcamento) {
        validarValorTotal(orcamento.getValorTotal());
        validarEstado(orcamento);
        validarResponsavel(orcamento);
    }

    private void validarValorTotal(BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor total do orçamento deve ser maior que zero.");
        }
    }

    private void validarEstado(Orcamento orcamento) {
        Estado estado = estadoRepository.findById(orcamento.getEstado().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + orcamento.getEstado().getId()));
        orcamento.setEstado(estado);
    }

    private void validarResponsavel(Orcamento orcamento) {
        Funcionario responsavel = funcionarioRepository.findById(orcamento.getResponsavel().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        FUNCIONARIO_NOT_FOUND_MESSAGE + orcamento.getResponsavel().getId()));
        orcamento.setResponsavel(responsavel);
    }
    private static final Logger LOG = Logger.getLogger(OrcamentoService.class.getName());
}
