package com.xicola.xicola.service;

import com.xicola.xicola.model.Sala;
import com.xicola.xicola.repository.SalaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalaService {

    private static final String SALA_NOT_FOUND_MESSAGE = "Sala não encontrada com o ID: ";

    private final SalaRepository salaRepository;

    @Transactional(readOnly = true)
    public Sala findById(Integer id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SALA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    @Transactional
    public Sala create(Sala sala) {
        validarSala(sala);

        return salaRepository.save(sala);
    }

    @Transactional
    public Sala update(Integer id, Sala salaAtualizada) {
        var salaExistente = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SALA_NOT_FOUND_MESSAGE + id));

        validarSala(salaAtualizada);

        salaExistente.setNomeSala(salaAtualizada.getNomeSala());
        salaExistente.setCapacidade(salaAtualizada.getCapacidade());

        return salaRepository.save(salaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        var sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SALA_NOT_FOUND_MESSAGE + id));

        salaRepository.delete(sala);
    }

    private void validarSala(Sala sala) {
        if (sala.getNomeSala() == null || sala.getNomeSala().isEmpty()) {
            throw new BadRequestException("O nome da sala não pode ser nulo ou vazio.");
        }

        if (sala.getCapacidade() <= 0) {
            throw new BadRequestException("A capacidade da sala deve ser maior que zero.");
        }

        // Adicione outras validações conforme necessário
    }
}
