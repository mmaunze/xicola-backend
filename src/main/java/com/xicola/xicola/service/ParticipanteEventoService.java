package com.xicola.xicola.service;

import com.xicola.xicola.model.Evento;
import com.xicola.xicola.model.ParticipanteEvento;
import com.xicola.xicola.model.TipoPessoa;
import com.xicola.xicola.repository.EventoRepository;
import com.xicola.xicola.repository.ParticipanteEventoRepository;
import com.xicola.xicola.repository.TipoPessoaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipanteEventoService {

    private static final String PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE = "Participante do evento não encontrado com o ID: ";
    private static final String EVENTO_NOT_FOUND_MESSAGE = "Evento não encontrado com o ID: ";
    private static final String TIPO_PESSOA_NOT_FOUND_MESSAGE = "Tipo de pessoa não encontrado com o ID: ";

    private final ParticipanteEventoRepository participanteEventoRepository;
    private final EventoRepository eventoRepository;
    private final TipoPessoaRepository tipoPessoaRepository;

    @Transactional(readOnly = true)
    public ParticipanteEvento findById(Integer id) {
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
    public ParticipanteEvento update(Integer id, ParticipanteEvento participanteEventoAtualizado) {
        var participanteEventoExistente = participanteEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PARTICIPANTE_EVENTO_NOT_FOUND_MESSAGE + id));

        validarParticipanteEvento(participanteEventoAtualizado);

        participanteEventoExistente.setNomeParticipante(participanteEventoAtualizado.getNomeParticipante());
        participanteEventoExistente.setEvento(participanteEventoAtualizado.getEvento());
        participanteEventoExistente.setTipoParticipante(participanteEventoAtualizado.getTipoParticipante());

        return participanteEventoRepository.save(participanteEventoExistente);
    }

    @Transactional
    public void delete(Integer id) {
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
