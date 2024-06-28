package com.xicola.xicola.service;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.AvaliacaoAluno;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.repository.AlunoRepository;
import com.xicola.xicola.repository.AvaliacaoAlunoRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvaliacaoAlunoService {

    private static final String AVALIACAO_ALUNO_NOT_FOUND_MESSAGE = "Avaliação do aluno não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String OBSERVACAO_VAZIA_MESSAGE = "A observação não pode estar vazia";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final AvaliacaoAlunoRepository avaliacaoAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public AvaliacaoAluno findById(Long id) {
        return avaliacaoAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AVALIACAO_ALUNO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<AvaliacaoAluno> findAll() {
        return avaliacaoAlunoRepository.findAll();
    }

    @Transactional
    public AvaliacaoAluno create(AvaliacaoAluno avaliacaoAluno) {
        // Obtém o estado "Activo" da base de dados
        var estadoOptional = estadoRepository.findEstado("Activo");
        // Verifica se o estado foi encontrado
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Verifica se o aluno foi encontrado
        Optional<Aluno> alunoOptional = alunoRepository.findById(avaliacaoAluno.getAluno().getId());
        var aluno = alunoOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        ALUNO_NOT_FOUND_MESSAGE + avaliacaoAluno.getAluno().getId()));

        // Realiza as validações necessárias
        validarDadosObrigatorios(avaliacaoAluno);

        // Define o estado e aluno na avaliação do aluno
        avaliacaoAluno.setEstado(estado);
        avaliacaoAluno.setAluno(aluno);

        // Salva a Avaliação do Aluno no banco de dados
        return avaliacaoAlunoRepository.save(avaliacaoAluno);
    }

    @Transactional
    public AvaliacaoAluno update(Long id, AvaliacaoAluno avaliacaoAlunoAtualizada) {
        var avaliacaoAlunoOptional = avaliacaoAlunoRepository.findById(id);
        if (avaliacaoAlunoOptional.isEmpty()) {
            throw new ResourceNotFoundException(AVALIACAO_ALUNO_NOT_FOUND_MESSAGE + id);
        }

        var avaliacaoAlunoExistente = avaliacaoAlunoOptional.get();

        validarDadosObrigatorios(avaliacaoAlunoAtualizada);

        // Atualiza outras propriedades conforme necessário
        avaliacaoAlunoExistente.setTrimestre(avaliacaoAlunoAtualizada.getTrimestre());
        avaliacaoAlunoExistente.setAnoLectivo(avaliacaoAlunoAtualizada.getAnoLectivo());
        avaliacaoAlunoExistente.setDataLancamento(avaliacaoAlunoAtualizada.getDataLancamento());
        avaliacaoAlunoExistente.setNota(avaliacaoAlunoAtualizada.getNota());
        avaliacaoAlunoExistente.setObservacao(avaliacaoAlunoAtualizada.getObservacao());

        // Verifica se o aluno foi encontrado
        Optional<Aluno> alunoOptional = alunoRepository.findById(avaliacaoAlunoAtualizada.getAluno().getId());
        var aluno = alunoOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        ALUNO_NOT_FOUND_MESSAGE + avaliacaoAlunoAtualizada.getAluno().getId()));
        avaliacaoAlunoExistente.setAluno(aluno);

        return avaliacaoAlunoRepository.save(avaliacaoAlunoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!avaliacaoAlunoRepository.existsById(id)) {
            throw new ResourceNotFoundException(AVALIACAO_ALUNO_NOT_FOUND_MESSAGE + id);
        }

        avaliacaoAlunoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(AvaliacaoAluno avaliacaoAluno) {
        if (avaliacaoAluno.getObservacao() == null || avaliacaoAluno.getObservacao().isBlank()) {
            throw new BadRequestException(OBSERVACAO_VAZIA_MESSAGE);
        }
    }
}
