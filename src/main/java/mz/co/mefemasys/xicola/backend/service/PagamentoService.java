package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.dto.create.CreatePagamentoDTO;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.repository.*;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PagamentoService implements MetodosGerais {

    private static final String PAGAMENTO_NOT_FOUND_MESSAGE = "Pagamento não encontrado com o ID: ";

    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";

    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionário não encontrado com o ID: ";

    private static final String TIPO_PAGAMENTO_NOT_FOUND_MESSAGE = "Tipo de pagamento não encontrado com o ID: ";

    private static final Logger LOG = Logger.getLogger(PagamentoService.class.getName());

    private final PagamentoRepository pagamentoRepository;

    private final AlunoRepository alunoRepository;

    private final EstadoRepository estadoRepository;

    private final FuncionarioRepository funcionarioRepository;

    private final TipoPagamentoRepository tipoPagamentoRepository;
    private final FuncionarioService funcionarioService;
    private final AlunoService alunoService;
    private final TipoPagamentoService tipoPagamentoService;
    private final EstadoService estadoService;
    private final MetodoPagamentoService metodoPagamentoService;

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
    public Pagamento create(CreatePagamentoDTO pagamento) {
        var newPagamento = new Pagamento();
        var responsavel = funcionarioService.findById(pagamento.getResponsavel());
        var aluno = alunoService.findById(pagamento.getAluno());
        var tipoPagamento = tipoPagamentoService.findTipo(pagamento.getTipoPagamento());
        var metodoPagamento = metodoPagamentoService.findMetodo(pagamento.getMetodoPagamento());
        var estado = estadoService.findEstado("Em processamento");
        var id = gerarId();

        newPagamento.setId (id);
        newPagamento.setAluno(aluno);
        newPagamento.setResponsavel(responsavel);
        newPagamento.setTipoPagamento(tipoPagamento);
        newPagamento.setDataPagamento(Instant.now());
        newPagamento.setValor(BigDecimal.valueOf(pagamento.getValor()));
        newPagamento.setEstado(estado);
        newPagamento.setReferencia(gerarReferencia(pagamento.getTipoPagamento()));

        if (pagamento.getObservacao() == null || pagamento.getObservacao().isBlank())
            newPagamento.setObservacao(tipoPagamento.getDescricao()+" Via "+metodoPagamento.getDescricao());
        else newPagamento.setObservacao(pagamento.getObservacao());

        newPagamento.setMetodoPagamento(metodoPagamento);

        return pagamentoRepository.save(newPagamento);

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

    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor do pagamento deve ser maior que zero.");

        }

    }

    private void validarDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento == null) {
            throw new BadRequestException("A data do pagamento não pode estar vazia.");

        }
        if (dataPagamento.isAfter(LocalDate.now())) {
            throw new BadRequestException("A data do pagamento não pode estar vazia.");

        }
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
