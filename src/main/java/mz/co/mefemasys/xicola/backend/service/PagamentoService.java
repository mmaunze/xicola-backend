package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private static final String PAGAMENTO_NOT_FOUND_MESSAGE = "Pagamento não encontrado com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionário não encontrado com o ID: ";
    private static final String TIPO_PAGAMENTO_NOT_FOUND_MESSAGE = "Tipo de pagamento não encontrado com o ID: ";

    private final PagamentoRepository pagamentoRepository;
    private final AlunoRepository alunoRepository;
    private final EstadoRepository estadoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TipoPagamentoRepository tipoPagamentoRepository;

    @Transactional(readOnly = true)
    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAGAMENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    @Transactional
    public Pagamento create(Pagamento pagamento) {
        validarPagamento(pagamento);

        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pagamento update(Long id, Pagamento pagamentoAtualizado) {
        var pagamentoExistente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAGAMENTO_NOT_FOUND_MESSAGE + id));

        validarPagamento(pagamentoAtualizado);

        pagamentoExistente.setReferencia(pagamentoAtualizado.getReferencia());
        pagamentoExistente.setValor(pagamentoAtualizado.getValor());
        pagamentoExistente.setDataPagamento(pagamentoAtualizado.getDataPagamento());
        pagamentoExistente.setObservacao(pagamentoAtualizado.getObservacao());
        pagamentoExistente.setAluno(pagamentoAtualizado.getAluno());
        pagamentoExistente.setEstado(pagamentoAtualizado.getEstado());
        pagamentoExistente.setResponsavel(pagamentoAtualizado.getResponsavel());
        pagamentoExistente.setTipoPagamento(pagamentoAtualizado.getTipoPagamento());

        return pagamentoRepository.save(pagamentoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAGAMENTO_NOT_FOUND_MESSAGE + id);
        }
        pagamentoRepository.deleteById(id);
    }

    private void validarPagamento(Pagamento pagamento) {
        validarReferencia(pagamento.getReferencia());
        validarValor(pagamento.getValor());
        validarDataPagamento(LocalDate.from(pagamento.getDataPagamento()));
        validarAluno(pagamento);
        validarEstado(pagamento);
        validarResponsavel(pagamento);
        validarTipoPagamento(pagamento);
    }

    private void validarReferencia(String referencia) {
        if (referencia == null || referencia.isEmpty()) {
            throw new BadRequestException("A referência do pagamento não pode estar vazia.");
        }
        // Aqui podem ser adicionadas outras validações específicas para a referência
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor do pagamento deve ser maior que zero.");
        }
        // Aqui podem ser adicionadas outras validações específicas para o valor
    }

    private void validarDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento == null) {
            throw new BadRequestException("A data do pagamento não pode estar vazia.");
        }
        if (dataPagamento.isAfter(LocalDate.now())) {
            throw new BadRequestException("A data do pagamento não pode estar vazia.");
        }

        // Aqui podem ser adicionadas outras validações específicas para a data de
        // pagamento
    }

    private void validarAluno(Pagamento pagamento) {
        Aluno aluno = alunoRepository.findById(pagamento.getAluno().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + pagamento.getAluno().getId()));
        pagamento.setAluno(aluno);
    }

    private void validarEstado(Pagamento pagamento) {
        Estado estado = estadoRepository.findById(pagamento.getEstado().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + pagamento.getEstado().getId()));
        pagamento.setEstado(estado);
    }

    private void validarResponsavel(Pagamento pagamento) {
        Funcionario responsavel = funcionarioRepository.findById(pagamento.getResponsavel().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        FUNCIONARIO_NOT_FOUND_MESSAGE + pagamento.getResponsavel().getId()));
        pagamento.setResponsavel(responsavel);
    }

    private void validarTipoPagamento(Pagamento pagamento) {
        TipoPagamento tipoPagamento = tipoPagamentoRepository.findById(pagamento.getTipoPagamento().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        TIPO_PAGAMENTO_NOT_FOUND_MESSAGE + pagamento.getTipoPagamento().getId()));
        pagamento.setTipoPagamento(tipoPagamento);
    }
}
