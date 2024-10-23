package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.TipoAvaliacao;
import mz.co.mefemasys.xicola.backend.repository.TipoAvaliacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoAvaliacaoService {

    private static final String TIPO_AVALIACAO_NOT_FOUND_MESSAGE = "Tipo de avaliação não encontrado com o ID: ";

    private final TipoAvaliacaoRepository tipoAvaliacaoRepository;

    @Transactional(readOnly = true)
    public TipoAvaliacao findById(Long id) {
        return tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<TipoAvaliacao> findAll() {
        return tipoAvaliacaoRepository.findAll();

    }

    @Transactional
    public TipoAvaliacao create(TipoAvaliacao tipoAvaliacao) {
        validarTipoAvaliacao(tipoAvaliacao);

        return tipoAvaliacaoRepository.save(tipoAvaliacao);

    }

    @Transactional
    public TipoAvaliacao update(Long id, TipoAvaliacao tipoAvaliacaoAtualizado) {
        var tipoAvaliacaoExistente = tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));

        validarTipoAvaliacao(tipoAvaliacaoAtualizado);

        tipoAvaliacaoExistente.setDescricao(tipoAvaliacaoAtualizado.getDescricao());

        return tipoAvaliacaoRepository.save(tipoAvaliacaoExistente);

    }

    @Transactional
    public void delete(Long id) {
        var tipoAvaliacao = tipoAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + id));

        tipoAvaliacaoRepository.delete(tipoAvaliacao);

    }

    private void validarTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        if (tipoAvaliacao.getDescricao() == null || tipoAvaliacao.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de avaliação não pode ser nula ou vazia.");

        }

    }

    public TipoAvaliacao findTipoAvaliacao(String tipo) {
        return tipoAvaliacaoRepository.findTipoAvaliacao(tipo)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_AVALIACAO_NOT_FOUND_MESSAGE + tipo));

    }
}
