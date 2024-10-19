package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Professor;
import mz.co.mefemasys.xicola.backend.models.Turma;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.ProfessorRepository;
import mz.co.mefemasys.xicola.backend.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final EstadoRepository estadoRepository;
    private final ProfessorRepository professorRepository;

    private static final String TURMA_NOT_FOUND_MESSAGE = "Turma não encontrada com o ID: ";
    private static final String NOME_TURMA_VAZIO_MESSAGE = "O nome da turma não pode estar vazio";
    private static final String ANO_LETIVO_INVALIDO_MESSAGE = "Ano letivo inválido";

    @Transactional(readOnly = true)
    public Turma findById(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TURMA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    @Transactional
    public Turma create(Turma turma) {
        validarTurma(turma);
        var estado = obterEstadoAtivo();
        turma.setEstado(estado);
        return turmaRepository.save(turma);
    }

    @Transactional
    public Turma update(Long id, Turma turmaAtualizada) {
        var turmaExistente = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TURMA_NOT_FOUND_MESSAGE + id));
        validarTurma(turmaAtualizada);
        turmaExistente.setNomeTurma(turmaAtualizada.getNomeTurma());
        turmaExistente.setAnoLectivo(turmaAtualizada.getAnoLectivo());
        return turmaRepository.save(turmaExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResourceNotFoundException(TURMA_NOT_FOUND_MESSAGE + id);
        }
        turmaRepository.deleteById(id);
    }


    private void validarTurma(Turma turma) {
        validarNomeTurma(turma.getNomeTurma());
        validarAnoLectivo(turma.getAnoLectivo());
        validarProfessorResponsavel(turma.getProfessorResponsavel());
    }

    private void validarNomeTurma(String nomeTurma) {
        if (nomeTurma == null || nomeTurma.isBlank()) {
            throw new BadRequestException(NOME_TURMA_VAZIO_MESSAGE);
        }
        // Aqui podem ser adicionadas outras validações específicas para o nome da turma
    }

    private void validarAnoLectivo(long anoLectivo) {
        // Exemplo de validação simples para o ano letivo
        if (anoLectivo < 1900 || anoLectivo > 2100) {
            throw new BadRequestException(ANO_LETIVO_INVALIDO_MESSAGE);
        }
    }

    private void validarProfessorResponsavel(Professor professor) {
        if (professor == null || professor.getId() == null) {
            throw new BadRequestException("Professor responsável não pode estar vazio");
        }
        Optional<Professor> professorOptional = professorRepository.findById(professor.getId());
        if (professorOptional.isEmpty()) {
            throw new BadRequestException("Professor responsável não encontrado com o ID: " + professor.getId());
        }
    }

    private Estado obterEstadoAtivo() {
        return estadoRepository.findEstado("Ativo")
                .orElseThrow(() -> new ResourceNotFoundException("Estado 'Ativo' não encontrado"));
    }
}
