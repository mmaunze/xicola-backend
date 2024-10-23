package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.FolhaPagamento;
import mz.co.mefemasys.xicola.backend.repository.FolhaPagamentoRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FolhaPagamentoService {

    private static final String FOLHA_PAGAMENTO_NOT_FOUND_MESSAGE = "Folha de pagamento não encontrada com o ID: ";

    private static final String SALARIO_BRUTO_NEGATIVO_MESSAGE = "O salário bruto não pode ser negativo";

    private static final String SALARIO_LIQUIDO_NEGATIVO_MESSAGE = "O salário líquido não pode ser negativo";

    private static final String MES_REFERENCIA_VAZIO_MESSAGE = "O mês de referência não pode estar vazio";

    private static final String DATA_PAGAMENTO_VAZIA_MESSAGE = "A data de pagamento não pode estar vazia";

    private static final Logger LOG = Logger.getLogger(FolhaPagamentoService.class.getName());

    private final FolhaPagamentoRepository folhaPagamentoRepository;

    @Transactional(readOnly = true)
    public FolhaPagamento findById(Long id) {
        return folhaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FOLHA_PAGAMENTO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<FolhaPagamento> findAll() {
        return folhaPagamentoRepository.findAll();

    }

    @Transactional
    public FolhaPagamento create(FolhaPagamento folhaPagamento) {
        validarFolhaPagamento(folhaPagamento);

        return folhaPagamentoRepository.save(folhaPagamento);

    }

    @Transactional
    public FolhaPagamento update(Long id, FolhaPagamento folhaPagamentoAtualizado) {
        var folhaPagamentoExistente = folhaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FOLHA_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        validarFolhaPagamento(folhaPagamentoAtualizado);

        folhaPagamentoExistente.setSalarioBruto(folhaPagamentoAtualizado.getSalarioBruto());

        folhaPagamentoExistente.setDescontos(folhaPagamentoAtualizado.getDescontos());

        folhaPagamentoExistente.setSalarioLiquido(folhaPagamentoAtualizado.getSalarioLiquido());

        folhaPagamentoExistente.setMesReferencia(folhaPagamentoAtualizado.getMesReferencia());

        folhaPagamentoExistente.setDataPagamento(folhaPagamentoAtualizado.getDataPagamento());

        folhaPagamentoExistente.setEstado(folhaPagamentoAtualizado.getEstado());

        folhaPagamentoExistente.setFuncionario(folhaPagamentoAtualizado.getFuncionario());

        // Aqui você pode adicionar outras atualizações conforme necessário
        return folhaPagamentoRepository.save(folhaPagamentoExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!folhaPagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(FOLHA_PAGAMENTO_NOT_FOUND_MESSAGE + id);

        }
        folhaPagamentoRepository.deleteById(id);

    }

    private void validarFolhaPagamento(FolhaPagamento folhaPagamento) {
        validarDadosObrigatorios(folhaPagamento);

        validarValores(folhaPagamento);

    }

    private void validarDadosObrigatorios(FolhaPagamento folhaPagamento) {
        if (folhaPagamento.getMesReferencia() == null) {
            throw new BadRequestException(MES_REFERENCIA_VAZIO_MESSAGE);

        }
        if (folhaPagamento.getDataPagamento() == null) {
            throw new BadRequestException(DATA_PAGAMENTO_VAZIA_MESSAGE);

        }
    }

    private void validarValores(FolhaPagamento folhaPagamento) {
        if (folhaPagamento.getSalarioBruto().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(SALARIO_BRUTO_NEGATIVO_MESSAGE);

        }
        if (folhaPagamento.getSalarioLiquido().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(SALARIO_LIQUIDO_NEGATIVO_MESSAGE);

        }
    }
}
