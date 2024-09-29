package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Disciplina;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.PautaTrimestral;
import mz.co.mefemasys.xicola.backend.models.Professor;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.DisciplinaRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.PautaTrimestralRepository;
import mz.co.mefemasys.xicola.backend.repository.ProfessorRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PautaTrimestralService {

    private static final String PAUTA_TRIMESTRAL_NOT_FOUND_MESSAGE = "Pauta trimestral não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "Professor não encontrado com o ID: ";

    private final PautaTrimestralRepository pautaTrimestralRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;
    private final ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public PautaTrimestral findById(Long id) {
        return pautaTrimestralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAUTA_TRIMESTRAL_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<PautaTrimestral> findAll() {
        return pautaTrimestralRepository.findAll();
    }

    @Transactional
    public PautaTrimestral create(PautaTrimestral pautaTrimestral) {
        validarPautaTrimestral(pautaTrimestral);

        return pautaTrimestralRepository.save(pautaTrimestral);
    }

    @Transactional
    public PautaTrimestral update(Long id, PautaTrimestral pautaTrimestralAtualizada) {
        var pautaTrimestralExistente = pautaTrimestralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAUTA_TRIMESTRAL_NOT_FOUND_MESSAGE + id));

        validarPautaTrimestral(pautaTrimestralAtualizada);

        pautaTrimestralExistente.setTrimestre(pautaTrimestralAtualizada.getTrimestre());
        pautaTrimestralExistente.setAnoLectivo(pautaTrimestralAtualizada.getAnoLectivo());
        pautaTrimestralExistente.setNotaFinal(pautaTrimestralAtualizada.getNotaFinal());
        pautaTrimestralExistente.setDataPublicacao(pautaTrimestralAtualizada.getDataPublicacao());
        pautaTrimestralExistente.setObservacao(pautaTrimestralAtualizada.getObservacao());
        pautaTrimestralExistente.setAluno(pautaTrimestralAtualizada.getAluno());
        pautaTrimestralExistente.setDisciplina(pautaTrimestralAtualizada.getDisciplina());
        pautaTrimestralExistente.setEstado(pautaTrimestralAtualizada.getEstado());
        pautaTrimestralExistente.setProfessor(pautaTrimestralAtualizada.getProfessor());

        return pautaTrimestralRepository.save(pautaTrimestralExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!pautaTrimestralRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAUTA_TRIMESTRAL_NOT_FOUND_MESSAGE + id);
        }
        pautaTrimestralRepository.deleteById(id);
    }

    private void validarPautaTrimestral(PautaTrimestral pautaTrimestral) {
        validarTrimestre(pautaTrimestral.getTrimestre());
        validarAnoLectivo(Long.valueOf(pautaTrimestral.getAnoLectivo()));
        validarNotaFinal(pautaTrimestral.getNotaFinal());
        validarAluno(pautaTrimestral);
        validarDisciplina(pautaTrimestral);
        validarEstado(pautaTrimestral);
        validarProfessor(pautaTrimestral);
    }

    private void validarTrimestre(long trimestre) {
        // Aqui podem ser adicionadas validações específicas para o trimestre
        // Exemplo simples: trimestre deve estar num intervalo específico
        if (trimestre < 1 || trimestre > 3) {
            throw new BadRequestException("O trimestre deve ser um valor entre 1 e 3.");
        }
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

    private void validarAluno(PautaTrimestral pautaTrimestral) {
        Aluno aluno = alunoRepository.findById(pautaTrimestral.getAluno().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ALUNO_NOT_FOUND_MESSAGE + pautaTrimestral.getAluno().getId()));
        pautaTrimestral.setAluno(aluno);
    }

    private void validarDisciplina(PautaTrimestral pautaTrimestral) {
        Disciplina disciplina = disciplinaRepository.findById(pautaTrimestral.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + pautaTrimestral.getDisciplina().getId()));
        pautaTrimestral.setDisciplina(disciplina);
    }

    private void validarEstado(PautaTrimestral pautaTrimestral) {
        Estado estado = estadoRepository.findById(pautaTrimestral.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ESTADO_NOT_FOUND_MESSAGE + pautaTrimestral.getEstado().getId()));
        pautaTrimestral.setEstado(estado);
    }

    private void validarProfessor(PautaTrimestral pautaTrimestral) {
        Professor professor = professorRepository.findById(pautaTrimestral.getProfessor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        PROFESSOR_NOT_FOUND_MESSAGE + pautaTrimestral.getProfessor().getId()));
        pautaTrimestral.setProfessor(professor);
    }
}
