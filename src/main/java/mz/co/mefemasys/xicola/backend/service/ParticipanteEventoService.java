package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.Evento;

import mz.co.mefemasys.xicola.backend.models.ParticipanteEvento;

import mz.co.mefemasys.xicola.backend.models.TipoPessoa;

import mz.co.mefemasys.xicola.backend.repository.EventoRepository;

import mz.co.mefemasys.xicola.backend.repository.ParticipanteEventoRepository;

import mz.co.mefemasys.xicola.backend.repository.TipoPessoaRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ParticipanteEventoService {

    private static final String PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE = "Participante do evento não encontrado com o ID: ";

    private static final String EVENTO_NOT_FOUND_MESSAGE = "Evento não encontrado com o ID: ";

    private static final String TIPO_PESSOA_NOT_FOUND_MESSAGE = "Tipo de pessoa não encontrado com o ID: ";

    private static final Logger LOG = Logger.getLogger(ParticipanteEventoService.class.getName());

    private final ParticipanteEventoRepository participanteEventoRepository;

    private final EventoRepository eventoRepository;

    private final TipoPessoaRepository tipoPessoaRepository;

    @Transactional(readOnly = true)
    public ParticipanteEvento findById(Long id) {
        return participanteEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<ParticipanteEvento> findAll() {
        return participanteEventoRepository.findAll();

    }

    @Transactional
    public ParticipanteEvento create(ParticipanteEvento participanteEvento) {
        validarParticipanteEvento(participanteEvento);

        return participanteEventoRepository.save(participanteEvento);

    }

    @Transactional
    public ParticipanteEvento update(Long id, ParticipanteEvento participanteEventoAtualizado) {
        var participanteEventoExistente = participanteEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE + id));

        validarParticipanteEvento(participanteEventoAtualizado);

        participanteEventoExistente.setNomeParticipante(participanteEventoAtualizado.getNomeParticipante());

        participanteEventoExistente.setEvento(participanteEventoAtualizado.getEvento());

        participanteEventoExistente.setTipoParticipante(participanteEventoAtualizado.getTipoParticipante());

        return participanteEventoRepository.save(participanteEventoExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!participanteEventoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE + id);

        }
        participanteEventoRepository.deleteById(id);

    }

    private void validarParticipanteEvento(ParticipanteEvento participanteEvento) {
        validarNomeParticipante(participanteEvento.getNomeParticipante());

        validarEvento(participanteEvento);

        validarTipoParticipante(participanteEvento);

    }

    private void validarNomeParticipante(String nomeParticipante) {
        if (nomeParticipante == null || nomeParticipante.isEmpty()) {
            throw new BadRequestException("O nome do participante não pode estar vazio.");

        }
        // Aqui podem ser adicionadas outras validações específicas para o nome do
        // participante
    }

    private void validarEvento(ParticipanteEvento participanteEvento) {
        Evento evento = eventoRepository.findById(participanteEvento.getEvento().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                EVENTO_NOT_FOUND_MESSAGE + participanteEvento.getEvento().getId()));

        participanteEvento.setEvento(evento);

    }

    private void validarTipoParticipante(ParticipanteEvento participanteEvento) {
        TipoPessoa tipoPessoa = tipoPessoaRepository.findById(participanteEvento.getTipoParticipante().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                TIPO_PESSOA_NOT_FOUND_MESSAGE + participanteEvento.getTipoParticipante().getId()));

        participanteEvento.setTipoParticipante(tipoPessoa);

    }
}
