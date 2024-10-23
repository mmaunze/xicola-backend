package mz.co.mefemasys.xicola.backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.dto.MetodoPagamentoDTO;
import mz.co.mefemasys.xicola.backend.models.MetodoPagamento;
import mz.co.mefemasys.xicola.backend.repository.AreaCientificaRepository;
import mz.co.mefemasys.xicola.backend.repository.MetodoPagamentoRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MetodoPagamentoService {

    private static final String METODO_PAGAMENTO_NOT_FOUND_MESSAGE = "Metodo de Pagamento não encontrada com o ID: ";

    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";

    private static final String DESCRICAO_CURTA_MESSAGE = "Descrição curta demais";

    private static final Logger LOG = Logger.getLogger(MetodoPagamentoService.class.getName());

    private final AreaCientificaRepository areaCientificaRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;

    @Transactional(readOnly = true)
    public MetodoPagamento findById(Long id) {
        return metodoPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(METODO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<MetodoPagamento> findAll() {
        return metodoPagamentoRepository.findAll();

    }

    @Transactional(readOnly = true)
    public MetodoPagamento findMetodo(String metodoPagamento) {
        return metodoPagamentoRepository.findMetodo(metodoPagamento)
                .orElseThrow(() -> new EntityNotFoundException(METODO_PAGAMENTO_NOT_FOUND_MESSAGE));

    }

    @Transactional
    public MetodoPagamento create(MetodoPagamentoDTO metodoPagamentoDTO) {
        var metodoPagamento = new MetodoPagamento();

        metodoPagamento.setDescricao(metodoPagamentoDTO.getNome());

        return metodoPagamentoRepository.save(metodoPagamento);

    }

    @Transactional
    public MetodoPagamento update(Long id, MetodoPagamentoDTO metodoPagamentoDTO) {

        var metodoExistente = metodoPagamentoRepository .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(METODO_PAGAMENTO_NOT_FOUND_MESSAGE + id));

        if (metodoPagamentoDTO.getNome() != null) {
            metodoExistente.setDescricao(metodoPagamentoDTO.getNome());
        }

        return metodoPagamentoRepository.save(metodoExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!metodoPagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(METODO_PAGAMENTO_NOT_FOUND_MESSAGE + id);

        }
        metodoPagamentoRepository.deleteById(id);

    }
}
