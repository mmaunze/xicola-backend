package com.xicola.xicola.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Avaliacao;
import com.xicola.xicola.model.Disciplina;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.TipoAvaliacao;
import com.xicola.xicola.repository.AvaliacaoRepository;
import com.xicola.xicola.repository.DisciplinaRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.TipoAvaliacaoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private static final String AVALIACAO_NOT_FOUND_MESSAGE = "Avaliação não encontrada com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String TIPO_AVALIACAO_NOT_FOUND_MESSAGE = "Tipo de Avaliação não encontrado com o ID: ";
    private static final String OBSERVACAO_VAZIA_MESSAGE = "A observação não pode estar vazia";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final AvaliacaoRepository avaliacaoRepository;
    private final AlunoService alunoService;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;
    private final TipoAvaliacaoRepository tipoAvaliacaoRepository;

    @Transactional(readOnly = true)
    public Avaliacao findById(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AVALIACAO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Avaliacao> findAll() {
        return avaliacaoRepository.findAll();
    }

    @Transactional
    public Avaliacao create(Avaliacao avaliacao) {
        // Obtém o estado "Activo" da base de dados
        Optional<Estado> estadoOptional = estadoRepository.findEstado("Activo");

        // Verifica se o estado foi encontrado
        Estado estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Verifica se o aluno foi encontrado
        Aluno aluno = alunoService.findById(avaliacao.getAluno().getId());

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(avaliacao.getDisciplina().getId());
        Disciplina disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + avaliacao.getDisciplina().getId()));

        // Verifica se o tipo de avaliação foi encontrado
        Optional<TipoAvaliacao> tipoAvaliacaoOptional = tipoAvaliacaoRepository
                .findById(avaliacao.getTipoAvaliacao().getId());
        TipoAvaliacao tipoAvaliacao = tipoAvaliacaoOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        TIPO_AVALIACAO_NOT_FOUND_MESSAGE + avaliacao.getTipoAvaliacao().getId()));

        // Realiza as validações necessárias
        validarDadosObrigatorios(avaliacao);

        // Define o estado, aluno, disciplina e tipo de avaliação na avaliação
        avaliacao.setEstado(estado);
        avaliacao.setAluno(aluno);
        avaliacao.setDisciplina(disciplina);
        avaliacao.setTipoAvaliacao(tipoAvaliacao);

        // Salva a Avaliação no banco de dados
        return avaliacaoRepository.save(avaliacao);
    }

    @Transactional
    public Avaliacao update(Long id, Avaliacao avaliacaoAtualizada) {
        Optional<Avaliacao> avaliacaoOptional = avaliacaoRepository.findById(id);
        if (avaliacaoOptional.isEmpty()) {
            throw new ResourceNotFoundException(AVALIACAO_NOT_FOUND_MESSAGE + id);
        }

        Avaliacao avaliacaoExistente = avaliacaoOptional.get();

        validarDadosObrigatorios(avaliacaoAtualizada);

        // Atualize outras propriedades conforme necessário
        avaliacaoExistente.setTrimestre(avaliacaoAtualizada.getTrimestre());
        avaliacaoExistente.setObservacao(avaliacaoAtualizada.getObservacao());

        // Verifica se o aluno foi encontrado
        Aluno aluno = alunoService.findById(avaliacaoAtualizada.getAluno().getId());
        avaliacaoExistente.setAluno(aluno);

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository
                .findById(avaliacaoAtualizada.getDisciplina().getId());
        Disciplina disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + avaliacaoAtualizada.getDisciplina().getId()));
        avaliacaoExistente.setDisciplina(disciplina);

        // Verifica se o tipo de avaliação foi encontrado
        Optional<TipoAvaliacao> tipoAvaliacaoOptional = tipoAvaliacaoRepository
                .findById(avaliacaoAtualizada.getTipoAvaliacao().getId());
        TipoAvaliacao tipoAvaliacao = tipoAvaliacaoOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        TIPO_AVALIACAO_NOT_FOUND_MESSAGE + avaliacaoAtualizada.getTipoAvaliacao().getId()));
        avaliacaoExistente.setTipoAvaliacao(tipoAvaliacao);

        return avaliacaoRepository.save(avaliacaoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException(AVALIACAO_NOT_FOUND_MESSAGE + id);
        }

        avaliacaoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Avaliacao avaliacao) {
        if (avaliacao.getObservacao() == null || avaliacao.getObservacao().isBlank()) {
            throw new BadRequestException(OBSERVACAO_VAZIA_MESSAGE);
        }
    }
}
