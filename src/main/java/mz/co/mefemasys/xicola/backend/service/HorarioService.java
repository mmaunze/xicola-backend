package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.repository.*;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService implements MetodosGerais {

    private static final String HORARIO_NOT_FOUND_MESSAGE = "Horário não encontrado com o ID: ";
    private static final String DIA_SEMANA_VAZIO_MESSAGE = "O dia da semana não pode estar vazio";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "Professor não encontrado com o ID: ";
    private static final String SALA_NOT_FOUND_MESSAGE = "Sala não encontrada com o ID: ";
    private static final String TURMA_NOT_FOUND_MESSAGE = "Turma não encontrada com o ID: ";
    private static final String HORA_INVALIDA_MESSAGE = "Hora Inválida";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final HorarioRepository horarioRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final SalaRepository salaRepository;
    private final TurmaRepository turmaRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Horario findById(Long id) {
        return horarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HORARIO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Transactional
    public Horario create(Horario horario) {
        validarHorario(horario);

        var estado = obterEstadoActivo();
        horario.setEstado(estado);

        return horarioRepository.save(horario);
    }

    @Transactional
    public Horario update(Long id, Horario horarioAtualizado) {
        var horarioExistente = horarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HORARIO_NOT_FOUND_MESSAGE + id));

        validarHorario(horarioAtualizado);

        atualizarHorario(horarioExistente, horarioAtualizado);

        return horarioRepository.save(horarioExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(HORARIO_NOT_FOUND_MESSAGE + id);
        }
        horarioRepository.deleteById(id);
    }

    private void validarHorario(Horario horario) {
        validarDadosObrigatorios(horario);

        validarHoraInicioFim(horario);
        validarDisciplina(horario);
        validarProfessor(horario);
        validarSala(horario);
        validarTurma(horario);
    }

    private void validarDadosObrigatorios(Horario horario) {
        if (horario.getDiaSemana() == null || horario.getDiaSemana().isBlank()) {
            throw new BadRequestException(DIA_SEMANA_VAZIO_MESSAGE);
        }
    }

    private void validarHoraInicioFim(Horario horario) {
        if (horario.getHoraInicio().isAfter(horario.getHoraTermino())) {
            throw new BadRequestException(
                    HORA_INVALIDA_MESSAGE + "A hora de início não pode ser depois da hora de término");
        }
    }

    private void validarDisciplina(Horario horario) {
        Disciplina disciplina = disciplinaRepository.findById(horario.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + horario.getDisciplina().getId()));
        horario.setDisciplina(disciplina);
    }

    private void validarProfessor(Horario horario) {
        Professor professor = professorRepository.findById(horario.getProfessor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        PROFESSOR_NOT_FOUND_MESSAGE + horario.getProfessor().getId()));
        horario.setProfessor(professor);
    }

    private void validarSala(Horario horario) {
        Sala sala = salaRepository.findById(horario.getSala().getId())
                .orElseThrow(() -> new ResourceNotFoundException(SALA_NOT_FOUND_MESSAGE + horario.getSala().getId()));
        horario.setSala(sala);
    }

    private void validarTurma(Horario horario) {
        Turma turma = turmaRepository.findById(horario.getTurma().getId())
                .orElseThrow(() -> new ResourceNotFoundException(TURMA_NOT_FOUND_MESSAGE + horario.getTurma().getId()));
        horario.setTurma(turma);
    }

    private void atualizarHorario(Horario horarioExistente, Horario horarioAtualizado) {
        horarioExistente.setDiaSemana(horarioAtualizado.getDiaSemana());
        horarioExistente.setHoraInicio(horarioAtualizado.getHoraInicio());
        horarioExistente.setHoraTermino(horarioAtualizado.getHoraTermino());
        horarioExistente.setDisciplina(horarioAtualizado.getDisciplina());
        horarioExistente.setProfessor(horarioAtualizado.getProfessor());
        horarioExistente.setSala(horarioAtualizado.getSala());
        horarioExistente.setTurma(horarioAtualizado.getTurma());
    }

    private Estado obterEstadoActivo() {
        return estadoRepository.findEstado("Activo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));
    }
}
