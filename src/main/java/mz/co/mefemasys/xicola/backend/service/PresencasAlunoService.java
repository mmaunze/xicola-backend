package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Disciplina;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.PresencasAluno;
import mz.co.mefemasys.xicola.backend.models.Turma;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.DisciplinaRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.PresencasAlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.TurmaRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PresencasAlunoService {

    private static final String PRESENCAS_ALUNO_NOT_FOUND_MESSAGE = "Presença do aluno não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String TURMA_NOT_FOUND_MESSAGE = "Turma não encontrada com o ID: ";

    private final PresencasAlunoRepository presencasAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;
    private final TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public PresencasAluno findById(Long id) {
        return presencasAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<PresencasAluno> findAll() {
        return presencasAlunoRepository.findAll();
    }

    @Transactional
    public PresencasAluno create(PresencasAluno presencasAluno) {
        validarPresencasAluno(presencasAluno);

        return presencasAlunoRepository.save(presencasAluno);
    }

    @Transactional
    public PresencasAluno update(Long id, PresencasAluno presencasAlunoAtualizada) {
        var presencasAlunoExistente = presencasAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id));

        validarPresencasAluno(presencasAlunoAtualizada);

        presencasAlunoExistente.setData(presencasAlunoAtualizada.getData());
        presencasAlunoExistente.setAluno(presencasAlunoAtualizada.getAluno());
        presencasAlunoExistente.setDisciplina(presencasAlunoAtualizada.getDisciplina());
        presencasAlunoExistente.setEstado(presencasAlunoAtualizada.getEstado());
        presencasAlunoExistente.setTurma(presencasAlunoAtualizada.getTurma());

        return presencasAlunoRepository.save(presencasAlunoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!presencasAlunoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id);
        }
        presencasAlunoRepository.deleteById(id);
    }

    private void validarPresencasAluno(PresencasAluno presencasAluno) {
        validarData(Date.from(presencasAluno.getData()));
        validarAluno(presencasAluno);
        validarDisciplina(presencasAluno);
        validarEstado(presencasAluno);
        validarTurma(presencasAluno);
    }

    private void validarData(Date data) {
        if (data.after(new Date())) {
            throw new BadRequestException("A data de presença não pode estar no futuro.");
        }
    }

    private void validarAluno(PresencasAluno presencasAluno) {
        Aluno aluno = alunoRepository.findById(presencasAluno.getAluno().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ALUNO_NOT_FOUND_MESSAGE + presencasAluno.getAluno().getId()));
        presencasAluno.setAluno(aluno);
    }

    private void validarDisciplina(PresencasAluno presencasAluno) {
        Disciplina disciplina = disciplinaRepository.findById(presencasAluno.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + presencasAluno.getDisciplina().getId()));
        presencasAluno.setDisciplina(disciplina);
    }

    private void validarEstado(PresencasAluno presencasAluno) {
        Estado estado = estadoRepository.findById(presencasAluno.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ESTADO_NOT_FOUND_MESSAGE + presencasAluno.getEstado().getId()));
        presencasAluno.setEstado(estado);
    }

    private void validarTurma(PresencasAluno presencasAluno) {
        Turma turma = turmaRepository.findById(presencasAluno.getTurma().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        TURMA_NOT_FOUND_MESSAGE + presencasAluno.getTurma().getId()));
        presencasAluno.setTurma(turma);
    }
}
