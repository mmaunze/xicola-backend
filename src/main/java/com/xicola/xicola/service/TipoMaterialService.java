package com.xicola.xicola.service;

import com.xicola.xicola.model.TipoMaterial;
import com.xicola.xicola.repository.TipoMaterialRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoMaterialService {

    private static final String TIPO_MATERIAL_NOT_FOUND_MESSAGE = "Tipo de material não encontrado com o ID: ";

    private final TipoMaterialRepository tipoMaterialRepository;

    @Transactional(readOnly = true)
    public TipoMaterial findById(Integer id) {
        return tipoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_MATERIAL_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<TipoMaterial> findAll() {
        return tipoMaterialRepository.findAll();
    }

    @Transactional
    public TipoMaterial create(TipoMaterial tipoMaterial) {
        validarTipoMaterial(tipoMaterial);

        return tipoMaterialRepository.save(tipoMaterial);
    }

    @Transactional
    public TipoMaterial update(Integer id, TipoMaterial tipoMaterialAtualizado) {
        var tipoMaterialExistente = tipoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_MATERIAL_NOT_FOUND_MESSAGE + id));

        validarTipoMaterial(tipoMaterialAtualizado);

        tipoMaterialExistente.setDescricao(tipoMaterialAtualizado.getDescricao());

        return tipoMaterialRepository.save(tipoMaterialExistente);
    }

    @Transactional
    public void delete(Integer id) {
        var tipoMaterial = tipoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_MATERIAL_NOT_FOUND_MESSAGE + id));

        tipoMaterialRepository.delete(tipoMaterial);
    }

    private void validarTipoMaterial(TipoMaterial tipoMaterial) {
        if (tipoMaterial.getDescricao() == null || tipoMaterial.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do tipo de material não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
