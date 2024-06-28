package com.xicola.xicola.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Aula;
import com.xicola.xicola.model.Disciplina;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.repository.AulaRepository;
import com.xicola.xicola.repository.DisciplinaRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AulaService {

    private static final String AULA_NOT_FOUND_MESSAGE = "Aula não encontrada com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String TITULO_VAZIO_MESSAGE = "O título não pode estar vazio";
    private static final String RESUMO_VAZIO_MESSAGE = "O resumo não pode estar vazio";
    private static final String CONTEUDO_VAZIO_MESSAGE = "O conteúdo não pode estar vazio";
    private static final String DATA_AULA_VAZIA_MESSAGE = "A data da aula não pode estar vazia";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final AulaRepository aulaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Aula findById(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AULA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Aula> findAll() {
        return aulaRepository.findAll();
    }

    @Transactional
    public Aula create(Aula aula) {
        // Obtém o estado "Activo" da base de dados
        Optional<Estado> estadoOptional = estadoRepository.findEstado("Activo");

        // Verifica se o estado foi encontrado
        Estado estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(aula.getDisciplina().getId());
        Disciplina disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + aula.getDisciplina().getId()));

        // Realiza as validações necessárias
        validarDadosObrigatorios(aula);

        // Define o estado e a disciplina na aula
        aula.setEstado(estado);
        aula.setDisciplina(disciplina);

        // Salva a Aula no banco de dados
        return aulaRepository.save(aula);
    }

    @Transactional
    public Aula update(Long id, Aula aulaAtualizada) {
        Optional<Aula> aulaOptional = aulaRepository.findById(id);
        if (aulaOptional.isEmpty()) {
            throw new ResourceNotFoundException(AULA_NOT_FOUND_MESSAGE + id);
        }

        Aula aulaExistente = aulaOptional.get();

        validarDadosObrigatorios(aulaAtualizada);

        // Atualize outras propriedades conforme necessário
        aulaExistente.setTitulo(aulaAtualizada.getTitulo());
        aulaExistente.setAnoLectivo(aulaAtualizada.getAnoLectivo());
        aulaExistente.setClasse(aulaAtualizada.getClasse());
        aulaExistente.setResumo(aulaAtualizada.getResumo());
        aulaExistente.setDataAula(aulaAtualizada.getDataAula());
        aulaExistente.setConteudo(aulaAtualizada.getConteudo());

        // Verifica se a disciplina foi encontrada
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(aulaAtualizada.getDisciplina().getId());
        Disciplina disciplina = disciplinaOptional
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + aulaAtualizada.getDisciplina().getId()));

        aulaExistente.setDisciplina(disciplina);

        return aulaRepository.save(aulaExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new ResourceNotFoundException(AULA_NOT_FOUND_MESSAGE + id);
        }

        aulaRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Aula aula) {
        if (aula.getTitulo() == null || aula.getTitulo().isBlank()) {
            throw new BadRequestException("Título da aula " + TITULO_VAZIO_MESSAGE);
        }
        if (aula.getResumo() == null || aula.getResumo().isBlank()) {
            throw new BadRequestException("Resumo da aula " + RESUMO_VAZIO_MESSAGE);
        }
        if (aula.getConteudo() == null || aula.getConteudo().isBlank()) {
            throw new BadRequestException("Conteúdo da aula " + CONTEUDO_VAZIO_MESSAGE);
        }
        if (aula.getDataAula() == null) {
            throw new BadRequestException("Data da aula " + DATA_AULA_VAZIA_MESSAGE);
        }
    }
}
