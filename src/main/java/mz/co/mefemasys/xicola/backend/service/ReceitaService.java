package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Receita;
import mz.co.mefemasys.xicola.backend.repository.ReceitaRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private static final String RECEITA_NOT_FOUND_MESSAGE = "Receita não encontrada com o ID: ";

    private static final Logger LOG = Logger.getLogger(ReceitaService.class.getName());

    private final ReceitaRepository receitaRepository;

    @Transactional(readOnly = true)
    public Receita findById(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECEITA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Receita> findAll() {
        return receitaRepository.findAll();

    }

    @Transactional
    public Receita create(Receita receita) {
        validarReceita(receita);

        return receitaRepository.save(receita);

    }

    @Transactional
    public Receita update(Long id, Receita receitaAtualizada) {
        var receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECEITA_NOT_FOUND_MESSAGE + id));

        validarReceita(receitaAtualizada);

        receitaExistente.setDescricao(receitaAtualizada.getDescricao());

        receitaExistente.setValor(receitaAtualizada.getValor());

        receitaExistente.setDataReceita(receitaAtualizada.getDataReceita());

        receitaExistente.setCategoria(receitaAtualizada.getCategoria());

        receitaExistente.setResponsavel(receitaAtualizada.getResponsavel());

        return receitaRepository.save(receitaExistente);

    }

    @Transactional
    public void delete(Long id) {
        var receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECEITA_NOT_FOUND_MESSAGE + id));

        receitaRepository.delete(receita);

    }

    private void validarReceita(Receita receita) {
        if (receita.getDescricao() == null || receita.getDescricao().trim().isEmpty()) {
            throw new BadRequestException("A descrição da receita não pode estar vazia.");

        }

        if (receita.getValor() == null || receita.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor da receita deve ser maior que zero.");

        }

        if (receita.getDataReceita() == null || receita.getDataReceita().isAfter(new Date().toInstant())) {
            throw new BadRequestException("A data da receita não pode ser nula e deve estar no passado.");

        }

        // Adicione outras validações conforme necessário
    }
}
