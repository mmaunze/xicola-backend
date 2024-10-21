package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.Avaliacao;

import mz.co.mefemasys.xicola.backend.models.Disciplina;

import mz.co.mefemasys.xicola.backend.models.TipoAvaliacao;

import mz.co.mefemasys.xicola.backend.repository.AvaliacaoRepository;

import mz.co.mefemasys.xicola.backend.repository.DisciplinaRepository;

import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;

import mz.co.mefemasys.xicola.backend.repository.TipoAvaliacaoRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private static final String AVALIACAO_NOT_FOUND_MESSAGE = "Avaliação não encontrada com o ID: ";

    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";

    private static final String TIPO_AVALIACAO_NOT_FOUND_MESSAGE = "Tipo de Avaliação não encontrado com o ID: ";

    private static final String OBSERVACAO_VAZIA_MESSAGE = "A observação não pode estar vazia";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private static final Logger LOG = Logger.getLogger(AvaliacaoService.class.getName());

    private final AvaliacaoRepository avaliacaoRepository;

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
        var estadoOptional = estadoRepository.findEstado("Activo");

        // Verifica se o estado foi encontrado
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(avaliacao.getDisciplina().getId());

        var disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                DISCIPLINA_NOT_FOUND_MESSAGE + avaliacao.getDisciplina().getId()));

        // Verifica se o tipo de avaliação foi encontrado
        Optional<TipoAvaliacao> tipoAvaliacaoOptional = tipoAvaliacaoRepository
                .findById(avaliacao.getTipoAvaliacao().getId());

        var tipoAvaliacao = tipoAvaliacaoOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                TIPO_AVALIACAO_NOT_FOUND_MESSAGE + avaliacao.getTipoAvaliacao().getId()));

        // Realiza as validações necessárias
        validarDadosObrigatorios(avaliacao);

        // Define o estado, aluno, disciplina e tipo de avaliação na avaliação
        avaliacao.setEstado(estado);

        avaliacao.setDisciplina(disciplina);

        avaliacao.setTipoAvaliacao(tipoAvaliacao);

        // Salva a Avaliação no banco de dados
        return avaliacaoRepository.save(avaliacao);

    }

    @Transactional
    public Avaliacao update(Long id, Avaliacao avaliacaoAtualizada) {
        var avaliacaoOptional = avaliacaoRepository.findById(id);

        if (avaliacaoOptional.isEmpty()) {
            throw new ResourceNotFoundException(AVALIACAO_NOT_FOUND_MESSAGE + id);

        }

        var avaliacaoExistente = avaliacaoOptional.get();

        validarDadosObrigatorios(avaliacaoAtualizada);

        // Atualize outras propriedades conforme necessário
        avaliacaoExistente.setTrimestre(avaliacaoAtualizada.getTrimestre());

        avaliacaoExistente.setObservacao(avaliacaoAtualizada.getObservacao());

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository
                .findById(avaliacaoAtualizada.getDisciplina().getId());

        var disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                DISCIPLINA_NOT_FOUND_MESSAGE + avaliacaoAtualizada.getDisciplina().getId()));

        avaliacaoExistente.setDisciplina(disciplina);

        // Verifica se o tipo de avaliação foi encontrado
        Optional<TipoAvaliacao> tipoAvaliacaoOptional = tipoAvaliacaoRepository
                .findById(avaliacaoAtualizada.getTipoAvaliacao().getId());

        var tipoAvaliacao = tipoAvaliacaoOptional
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
