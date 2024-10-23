package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.TipoPessoa;
import mz.co.mefemasys.xicola.backend.repository.TipoPessoaRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPessoaService {

    private static final String TIPO_PESSOA_NOT_FOUND_MESSAGE = "Tipo de pessoa não encontrado com o ID: ";

    private final TipoPessoaRepository tipoPessoaRepository;

    @Transactional(readOnly = true)
    public TipoPessoa findById(Long id) {
        return tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<TipoPessoa> findAll() {
        return tipoPessoaRepository.findAll();

    }

    @Transactional
    public TipoPessoa create(TipoPessoa tipoPessoa) {
        validarTipoPessoa(tipoPessoa);

        return tipoPessoaRepository.save(tipoPessoa);

    }

    @Transactional
    public TipoPessoa update(Long id, TipoPessoa tipoPessoaAtualizado) {
        var tipoPessoaExistente = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));

        validarTipoPessoa(tipoPessoaAtualizado);

        tipoPessoaExistente.setDescricao(tipoPessoaAtualizado.getDescricao());

        return tipoPessoaRepository.save(tipoPessoaExistente);

    }

    @Transactional
    public void delete(Long id) {
        var tipoPessoa = tipoPessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + id));

        tipoPessoaRepository.delete(tipoPessoa);

    }

    private void validarTipoPessoa(TipoPessoa tipoPessoa) {
        if (tipoPessoa.getDescricao() == null || tipoPessoa.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de pessoa não pode ser nula ou vazia.");

        }

        // Adicione outras validações conforme necessário
    }

    public TipoPessoa findDestinatario(String destinatario) {
        return tipoPessoaRepository.findTipoPessoa(destinatario)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_PESSOA_NOT_FOUND_MESSAGE + destinatario));

    }
}
