package com.xicola.xicola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Disciplina;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.PautaFinal;
import com.xicola.xicola.model.Professor;
import com.xicola.xicola.repository.AlunoRepository;
import com.xicola.xicola.repository.DisciplinaRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.PautaFinalRepository;
import com.xicola.xicola.repository.ProfessorRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaFinalService {

    private static final String PAUTA_FINAL_NOT_FOUND_MESSAGE = "Pauta final não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "Professor não encontrado com o ID: ";

    private final PautaFinalRepository pautaFinalRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;
    private final ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public PautaFinal findById(Long id) {
        return pautaFinalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAUTA_FINAL_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<PautaFinal> findAll() {
        return pautaFinalRepository.findAll();
    }

    @Transactional
    public PautaFinal create(PautaFinal pautaFinal) {
        validarPautaFinal(pautaFinal);

        return pautaFinalRepository.save(pautaFinal);
    }

    @Transactional
    public PautaFinal update(Long id, PautaFinal pautaFinalAtualizada) {
        PautaFinal pautaFinalExistente = pautaFinalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAUTA_FINAL_NOT_FOUND_MESSAGE + id));

        validarPautaFinal(pautaFinalAtualizada);

        pautaFinalExistente.setAnoLectivo(pautaFinalAtualizada.getAnoLectivo());
        pautaFinalExistente.setNotaFinal(pautaFinalAtualizada.getNotaFinal());
        pautaFinalExistente.setDataPublicacao(pautaFinalAtualizada.getDataPublicacao());
        pautaFinalExistente.setResultado(pautaFinalAtualizada.getResultado());
        pautaFinalExistente.setAluno(pautaFinalAtualizada.getAluno());
        pautaFinalExistente.setDisciplina(pautaFinalAtualizada.getDisciplina());
        pautaFinalExistente.setEstadoPauta(pautaFinalAtualizada.getEstadoPauta());
        pautaFinalExistente.setProfessor(pautaFinalAtualizada.getProfessor());

        return pautaFinalRepository.save(pautaFinalExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!pautaFinalRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAUTA_FINAL_NOT_FOUND_MESSAGE + id);
        }
        pautaFinalRepository.deleteById(id);
    }

    private void validarPautaFinal(PautaFinal pautaFinal) {
        validarAnoLectivo(Long.valueOf(pautaFinal.getAnoLectivo()));
        validarNotaFinal(pautaFinal.getNotaFinal());
        validarResultado(pautaFinal.getResultado());
        validarAluno(pautaFinal);
        validarDisciplina(pautaFinal);
        validarEstadoPauta(pautaFinal);
        validarProfessor(pautaFinal);
    }

    private void validarAnoLectivo(Long anoLectivo) {
        // Aqui podem ser adicionadas validações específicas para o ano letivo
        // Exemplo simples: ano letivo deve ser maior que zero
        if (anoLectivo == null || anoLectivo <= 0) {
            throw new BadRequestException("O ano letivo deve ser um número positivo.");
        }
    }

    private void validarNotaFinal(long notaFinal) {
        // Aqui podem ser adicionadas validações específicas para a nota final
        // Exemplo simples: nota final deve estar dentro de um intervalo específico
        if (notaFinal < 0 || notaFinal > 20) {
            throw new BadRequestException("A nota final deve estar entre 0 e 20.");
        }
    }

    private void validarResultado(String resultado) {
        // Aqui podem ser adicionadas validações específicas para o resultado
        // Exemplo simples: resultado não pode estar vazio
        if (resultado == null || resultado.isEmpty()) {
            throw new BadRequestException("O resultado não pode estar vazio.");
        }
    }

    private void validarAluno(PautaFinal pautaFinal) {
        Aluno aluno = alunoRepository.findById(pautaFinal.getAluno().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + pautaFinal.getAluno().getId()));
        pautaFinal.setAluno(aluno);
    }

    private void validarDisciplina(PautaFinal pautaFinal) {
        Disciplina disciplina = disciplinaRepository.findById(pautaFinal.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + pautaFinal.getDisciplina().getId()));
        pautaFinal.setDisciplina(disciplina);
    }

    private void validarEstadoPauta(PautaFinal pautaFinal) {
        Estado estado = estadoRepository.findById(pautaFinal.getEstadoPauta().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ESTADO_NOT_FOUND_MESSAGE + pautaFinal.getEstadoPauta().getId()));
        pautaFinal.setEstadoPauta(estado);
    }

    private void validarProfessor(PautaFinal pautaFinal) {
        Professor professor = professorRepository.findById(pautaFinal.getProfessor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        PROFESSOR_NOT_FOUND_MESSAGE + pautaFinal.getProfessor().getId()));
        pautaFinal.setProfessor(professor);
    }
}
