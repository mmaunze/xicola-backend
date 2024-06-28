package com.xicola.xicola.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Disciplina;
import com.xicola.xicola.repository.DisciplinaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String NOME_DISCIPLINA_VAZIO_MESSAGE = "O nome da disciplina não pode estar vazio";

    private final DisciplinaRepository disciplinaRepository;

    @Transactional(readOnly = true)
    public Disciplina findById(Integer id) {
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
    public Disciplina update(Integer id, Disciplina disciplinaAtualizada) {
        validarDisciplinaExistente(id);
        validarNomeDisciplina(disciplinaAtualizada.getNomeDisciplina());

        Disciplina disciplinaExistente = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + id));

        mapearDisciplinaAtualizada(disciplinaExistente, disciplinaAtualizada);

        return disciplinaRepository.save(disciplinaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        validarDisciplinaExistente(id);
        disciplinaRepository.deleteById(id);
    }

    private void validarNomeDisciplina(String nomeDisciplina) {
        if (nomeDisciplina == null || nomeDisciplina.isEmpty()) {
            throw new BadRequestException(NOME_DISCIPLINA_VAZIO_MESSAGE);
        }
    }

    private void validarDisciplinaExistente(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new ResourceNotFoundException(DISCIPLINA_NOT_FOUND_MESSAGE + id);
        }
    }

    private void mapearDisciplinaAtualizada(Disciplina disciplinaExistente, Disciplina disciplinaAtualizada) {
        disciplinaExistente.setNomeDisciplina(disciplinaAtualizada.getNomeDisciplina());
        // Outros mapeamentos de atributos podem ser adicionados conforme necessário
    }
}
