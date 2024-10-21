package mz.co.mefemasys.xicola.backend.service;

import java.math.BigDecimal;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Contrato;
import mz.co.mefemasys.xicola.backend.repository.ContratoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private static final String CONTRATO_NOT_FOUND_MESSAGE = "Contrato não encontrado com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final ContratoRepository contratoRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Contrato findById(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CONTRATO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    @Transactional
    public Contrato create(Contrato contrato) {
        validarContrato(contrato);
        return contratoRepository.save(contrato);
    }

    @Transactional
    public Contrato update(Long id, Contrato contratoAtualizado) {
        validarContrato(contratoAtualizado);

        var contratoOptional = contratoRepository.findById(id);
        if (contratoOptional.isEmpty()) {
            throw new ResourceNotFoundException(CONTRATO_NOT_FOUND_MESSAGE + id);
        }

        var contratoExistente = contratoOptional.get();
        mapearContratoAtualizado(contratoExistente, contratoAtualizado);

        return contratoRepository.save(contratoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CONTRATO_NOT_FOUND_MESSAGE + id);
        }

        contratoRepository.deleteById(id);
    }

    private void validarContrato(Contrato contrato) {
        validarCampoObrigatorio(contrato.getDescricao(), "Descrição");
        validarCampoObrigatorio(contrato.getTipo(), "Tipo");
        validarCampoObrigatorio(contrato.getDataInicio(), "Data de Início");
        validarValorTotal(contrato.getValorTotal());
        validarCampoObrigatorio(contrato.getFornecedor(), "Fornecedor");

        var estadoOptional = estadoRepository.findEstado("Ativo");
        var estado = estadoOptional.orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));
        contrato.setEstado(estado);
    }

    private void validarCampoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new BadRequestException(nomeCampo + " não pode estar vazio");
        }
    }

    private void validarCampoObrigatorio(Object valor, String nomeCampo) {
        if (valor == null) {
            throw new BadRequestException(nomeCampo + " não pode estar vazio");
        }
    }

    private void validarValorTotal(BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Valor total do contrato não pode ser negativo");
        }
    }

    private void mapearContratoAtualizado(Contrato contratoExistente, Contrato contratoAtualizado) {
        contratoExistente.setDescricao(contratoAtualizado.getDescricao());
        contratoExistente.setTipo(contratoAtualizado.getTipo());
        contratoExistente.setDataInicio(contratoAtualizado.getDataInicio());
        contratoExistente.setDataFim(contratoAtualizado.getDataFim());
        contratoExistente.setValorTotal(contratoAtualizado.getValorTotal());
        contratoExistente.setFornecedor(contratoAtualizado.getFornecedor());
    }
    private static final Logger LOG = Logger.getLogger(ContratoService.class.getName());
}
