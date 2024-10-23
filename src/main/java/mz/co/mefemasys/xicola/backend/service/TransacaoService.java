package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Transacao;
import mz.co.mefemasys.xicola.backend.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private static final String TRANSACAO_NOT_FOUND_MESSAGE = "Transação não encontrada com o ID: ";

    private static final Logger LOG = Logger.getLogger(TransacaoService.class.getName());

    private final TransacaoRepository transacaoRepository;

    @Transactional(readOnly = true)
    public Transacao findById(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TRANSACAO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Transacao> findAll() {
        return transacaoRepository.findAll();

    }

    @Transactional
    public Transacao create(Transacao transacao) {
        validarTransacao(transacao);

        return transacaoRepository.save(transacao);

    }

    @Transactional
    public Transacao update(Long id, Transacao transacaoAtualizada) {
        var transacaoExistente = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TRANSACAO_NOT_FOUND_MESSAGE + id));

        validarTransacao(transacaoAtualizada);

        transacaoExistente.setValor(transacaoAtualizada.getValor());

        transacaoExistente.setDataTransacao(transacaoAtualizada.getDataTransacao());

        transacaoExistente.setDescricao(transacaoAtualizada.getDescricao());

        transacaoExistente.setCategoria(transacaoAtualizada.getCategoria());

        transacaoExistente.setEstado(transacaoAtualizada.getEstado());

        transacaoExistente.setResponsavel(transacaoAtualizada.getResponsavel());

        transacaoExistente.setTipoTransacao(transacaoAtualizada.getTipoTransacao());

        return transacaoRepository.save(transacaoExistente);

    }

    @Transactional
    public void delete(Long id) {
        var transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TRANSACAO_NOT_FOUND_MESSAGE + id));

        transacaoRepository.delete(transacao);

    }

    private void validarTransacao(Transacao transacao) {
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor da transação deve ser maior que zero.");

        }

        if (transacao.getDataTransacao() == null || transacao.getDataTransacao().isAfter(new Date().toInstant())) {
            throw new BadRequestException("A data da transação não pode estar no futuro.");

        }

        if (transacao.getDescricao() == null || transacao.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição da transação não pode ser nula ou vazia.");

        }

        if (transacao.getCategoria() == null || transacao.getEstado() == null
                || transacao.getResponsavel() == null || transacao.getTipoTransacao() == null) {
            throw new BadRequestException("Todos os campos obrigatórios devem ser preenchidos.");

        }

    }
}
