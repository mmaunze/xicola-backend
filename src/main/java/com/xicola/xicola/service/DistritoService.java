package com.xicola.xicola.service;

import com.xicola.xicola.model.Distrito;
import com.xicola.xicola.repository.DistritoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DistritoService {

    private static final String DISTRICT_NOT_FOUND_MESSAGE = "Distrito não encontrado com o ID: %d";
    private static final String DISTRICT_NAME_REQUIRED_MESSAGE = "O nome do distrito é obrigatório";

    private final DistritoRepository distritoRepository;

    @Transactional(readOnly = true)
    public Iterable<Distrito> findAll() {
        return distritoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Distrito findById(Integer id) {
        return distritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id)));
    }

    @Transactional
    public Distrito create(Distrito distrito) {
        validarCamposObrigatorios(distrito);
        return distritoRepository.save(distrito);
    }

    @Transactional
    public Distrito update(Integer id, Distrito distritoAtualizado) {
        validarDistritoExistente(id);
        validarCamposObrigatorios(distritoAtualizado);

        var distritoExistente = distritoRepository.findById(id)               
         .orElseThrow(() -> new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id)));
        mapearDistritoAtualizado(distritoExistente, distritoAtualizado);

        return distritoRepository.save(distritoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        validarDistritoExistente(id);
        distritoRepository.deleteById(id);
    }

    private void validarCamposObrigatorios(Distrito distrito) {
        if (distrito.getNomeDistrito() == null || distrito.getNomeDistrito().isEmpty()) {
            throw new BadRequestException(DISTRICT_NAME_REQUIRED_MESSAGE);
        }
    }

    private void validarDistritoExistente(Integer id) {
        if (!distritoRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id));
        }
    }

    private void mapearDistritoAtualizado(Distrito distritoExistente, Distrito distritoAtualizado) {
        distritoExistente.setNomeDistrito(distritoAtualizado.getNomeDistrito());
        
    }
}
