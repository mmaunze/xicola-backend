package com.xicola.xicola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Matricula;
import com.xicola.xicola.repository.AlunoRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.MatriculaRepository;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private static final String MATRICULA_NOT_FOUND_MESSAGE = "Matrícula não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Matricula findById(Integer id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATRICULA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Matricula> findAll() {
        return matriculaRepository.findAll();
    }

    @Transactional
    public Matricula create(Matricula matricula) {
        validarMatricula(matricula);

        return matriculaRepository.save(matricula);
    }

    @Transactional
    public Matricula update(Integer id, Matricula matriculaAtualizada) {
        Matricula matriculaExistente = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATRICULA_NOT_FOUND_MESSAGE + id));

        validarMatricula(matriculaAtualizada);

        matriculaExistente.setAnoLectivo(matriculaAtualizada.getAnoLectivo());
        matriculaExistente.setAluno(matriculaAtualizada.getAluno());
        matriculaExistente.setEstado(matriculaAtualizada.getEstado());

        return matriculaRepository.save(matriculaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!matriculaRepository.existsById(id)) {
            throw new ResourceNotFoundException(MATRICULA_NOT_FOUND_MESSAGE + id);
        }
        matriculaRepository.deleteById(id);
    }

    private void validarMatricula(Matricula matricula) {
        validarAluno(matricula);
        validarEstado(matricula);
    }

    private void validarAluno(Matricula matricula) {
        Aluno aluno = alunoRepository.findById(matricula.getAluno().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + matricula.getAluno().getId()));
        matricula.setAluno(aluno);
    }

    private void validarEstado(Matricula matricula) {
        Estado estado = estadoRepository.findById(matricula.getEstado().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + matricula.getEstado().getId()));
        matricula.setEstado(estado);
    }
}
