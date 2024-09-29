package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Evento;
import mz.co.mefemasys.xicola.backend.repository.EventoRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private static final String EVENTO_NOT_FOUND_MESSAGE = "Evento não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome do evento não pode estar vazio";
    private static final String DESCRICAO_LONGA_MESSAGE = "A descrição do evento é muito longa";
    private static final String DATA_INICIO_VAZIA_MESSAGE = "A data de início do evento não pode estar vazia";
    private static final String DATA_FIM_INVALIDA_MESSAGE = "Data de fim do evento inválida";
    private static final String LOCALIZACAO_LONGA_MESSAGE = "A localização do evento é muito longa";

    private final EventoRepository eventoRepository;

    @Transactional(readOnly = true)
    public Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EVENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    @Transactional
    public Evento create(Evento evento) {
        validarEvento(evento);
        return eventoRepository.save(evento);
    }

    @Transactional
    public Evento update(Long id, Evento eventoAtualizado) {
        var eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EVENTO_NOT_FOUND_MESSAGE + id));

        validarEvento(eventoAtualizado);

        eventoExistente.setNome(eventoAtualizado.getNome());
        eventoExistente.setDescricao(eventoAtualizado.getDescricao());
        eventoExistente.setDataInicio(eventoAtualizado.getDataInicio());
        eventoExistente.setDataFim(eventoAtualizado.getDataFim());
        eventoExistente.setLocalizacao(eventoAtualizado.getLocalizacao());
        eventoExistente.setEstado(eventoAtualizado.getEstado());
        eventoExistente.setResponsavel(eventoAtualizado.getResponsavel());
        // Aqui você pode adicionar outras atualizações conforme necessário

        return eventoRepository.save(eventoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException(EVENTO_NOT_FOUND_MESSAGE + id);
        }
        eventoRepository.deleteById(id);
    }

    private void validarEvento(Evento evento) {
        validarDadosObrigatorios(evento);
        validarComprimentoMaximo(evento);
        validarDatas(evento);
    }

    private void validarDadosObrigatorios(Evento evento) {
        if (evento.getNome() == null || evento.getNome().isBlank()) {
            throw new BadRequestException("Nome do evento " + NOME_VAZIO_MESSAGE);
        }
        if (evento.getDataInicio() == null) {
            throw new BadRequestException(DATA_INICIO_VAZIA_MESSAGE);
        }
    }

    private void validarComprimentoMaximo(Evento evento) {
        if (evento.getDescricao() != null && evento.getDescricao().length() > 1000) {
            throw new BadRequestException(DESCRICAO_LONGA_MESSAGE);
        }
        if (evento.getLocalizacao() != null && evento.getLocalizacao().length() > 255) {
            throw new BadRequestException(LOCALIZACAO_LONGA_MESSAGE);
        }
    }

    private void validarDatas(Evento evento) {
        if (evento.getDataFim() != null && evento.getDataInicio().isAfter(evento.getDataFim())) {
            throw new BadRequestException(DATA_FIM_INVALIDA_MESSAGE);
        }
    }
}
