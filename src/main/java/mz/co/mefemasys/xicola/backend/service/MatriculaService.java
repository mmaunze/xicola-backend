package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Matricula;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.MatriculaRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private static final String MATRICULA_NOT_FOUND_MESSAGE = "Matrícula não encontrada com o ID: ";

    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";

    private static final Logger LOG = Logger.getLogger(MatriculaService.class.getName());

    private final MatriculaRepository matriculaRepository;

    private final AlunoRepository alunoRepository;

    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Matricula findById(Long id) {
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
    public Matricula update(Long id, Matricula matriculaAtualizada) {
        var matriculaExistente = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATRICULA_NOT_FOUND_MESSAGE + id));

        validarMatricula(matriculaAtualizada);

        matriculaExistente.setAnoLectivo(matriculaAtualizada.getAnoLectivo());

        matriculaExistente.setAluno(matriculaAtualizada.getAluno());

        matriculaExistente.setEstado(matriculaAtualizada.getEstado());

        return matriculaRepository.save(matriculaExistente);

    }

    @Transactional
    public void delete(Long id) {
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
