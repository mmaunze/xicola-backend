package com.xicola.xicola.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xicola.xicola.model.Cargo;
import com.xicola.xicola.repository.CargoRepository;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CargoService {

    private static final String CARGO_NOT_FOUND_MESSAGE = "Cargo não encontrado com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição do cargo não pode estar vazia";

    private final CargoRepository cargoRepository;

    @Transactional(readOnly = true)
    public Cargo findById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CARGO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    @Transactional
    public Cargo create(Cargo cargo) {
        validarDadosObrigatorios(cargo);
        return cargoRepository.save(cargo);
    }

    @Transactional
    public Cargo update(Long id, Cargo cargoAtualizado) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);
        if (cargoOptional.isEmpty()) {
            throw new ResourceNotFoundException(CARGO_NOT_FOUND_MESSAGE + id);
        }

        Cargo cargoExistente = cargoOptional.get();
        validarDadosObrigatorios(cargoAtualizado);
        cargoExistente.setDescricao(cargoAtualizado.getDescricao());

        return cargoRepository.save(cargoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CARGO_NOT_FOUND_MESSAGE + id);
        }

        cargoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Cargo cargo) {
        if (cargo.getDescricao() == null || cargo.getDescricao().isBlank()) {
            throw new ResourceNotFoundException(DESCRICAO_VAZIA_MESSAGE);
        }
    }
}
