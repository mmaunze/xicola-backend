package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Disciplina;
import mz.co.mefemasys.xicola.backend.repository.DisciplinaRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";

    private static final String NOME_DISCIPLINA_VAZIO_MESSAGE = "O nome da disciplina não pode estar vazio";

    private static final Logger LOG = Logger.getLogger(DisciplinaService.class.getName());

    private final DisciplinaRepository disciplinaRepository;

    @Transactional(readOnly = true)
    public Disciplina findById(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public Iterable<Disciplina> findAll() {
        return disciplinaRepository.findAll();

    }

    @Transactional
    public Disciplina create(Disciplina disciplina) {
        validarNomeDisciplina(disciplina.getNomeDisciplina());

        return disciplinaRepository.save(disciplina);

    }

    @Transactional
    public Disciplina update(Long id, Disciplina disciplinaAtualizada) {
        validarDisciplinaExistente(id);

        validarNomeDisciplina(disciplinaAtualizada.getNomeDisciplina());

        var disciplinaExistente = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + id));

        mapearDisciplinaAtualizada(disciplinaExistente, disciplinaAtualizada);

        return disciplinaRepository.save(disciplinaExistente);

    }

    @Transactional
    public void delete(Long id) {
        validarDisciplinaExistente(id);

        disciplinaRepository.deleteById(id);

    }

    private void validarNomeDisciplina(String nomeDisciplina) {
        if (nomeDisciplina == null || nomeDisciplina.isEmpty()) {
            throw new BadRequestException(NOME_DISCIPLINA_VAZIO_MESSAGE);

        }
    }

    private void validarDisciplinaExistente(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + id);

        }
    }

    private void mapearDisciplinaAtualizada(Disciplina disciplinaExistente, Disciplina disciplinaAtualizada) {
        disciplinaExistente.setNomeDisciplina(disciplinaAtualizada.getNomeDisciplina());

        // Outros mapeamentos de atributos podem ser adicionados conforme necessário
    }

    @Transactional(readOnly = true)
    public Disciplina findDisciplina(String disciplina) {
        return disciplinaRepository.findDisciplina(disciplina)
                .orElseThrow(() -> new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + disciplina));

    }
}
