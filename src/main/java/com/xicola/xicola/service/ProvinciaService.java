package com.xicola.xicola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Provincia;

import com.xicola.xicola.repository.ProvinciaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinciaService {

    private static final String PROVINCIA_NOT_FOUND_MESSAGE = "Provincia não encontrada com o ID: ";

    private final ProvinciaRepository provinciaRepository;

    @Transactional(readOnly = true)
    public Provincia findById(Integer id) {
        return provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVINCIA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    @Transactional
    public Provincia create(Provincia provincia) {
        validarNomeProvincia(provincia.getNomeProvincia());

        return provinciaRepository.save(provincia);
    }

    @Transactional
    public Provincia update(Integer id, Provincia provinciaAtualizada) {
        Provincia provinciaExistente = provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVINCIA_NOT_FOUND_MESSAGE + id));

        validarNomeProvincia(provinciaAtualizada.getNomeProvincia());

        provinciaExistente.setNomeProvincia(provinciaAtualizada.getNomeProvincia());

        return provinciaRepository.save(provinciaExistente);
    }

    @Transactional
    public void delete(Integer id) {

        provinciaRepository.deleteById(id);
    }

    private void validarNomeProvincia(String nomeProvincia) {
        if (nomeProvincia == null || nomeProvincia.trim().isEmpty()) {
            throw new BadRequestException("O nome da província não pode estar vazio.");
        }

        if (provinciaRepository.findByNomeProvincia(nomeProvincia).isPresent()) {
            throw new BadRequestException("Já existe uma província com o nome fornecido: " + nomeProvincia);
        }
    }
}
