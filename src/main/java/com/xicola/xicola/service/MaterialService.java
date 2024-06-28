package com.xicola.xicola.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Material;
import com.xicola.xicola.model.TipoMaterial;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.MaterialRepository;
import com.xicola.xicola.repository.TipoMaterialRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private static final String MATERIAL_NOT_FOUND_MESSAGE = "Material não encontrado com o ID: ";
    private static final String NOME_MATERIAL_DUPLICADO_MESSAGE = "Já existe um material cadastrado com o nome: ";
    private static final String QUANTIDADE_NEGATIVA_MESSAGE = "A quantidade do material não pode ser negativa";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String TIPO_MATERIAL_NOT_FOUND_MESSAGE = "Tipo de material não encontrado com o ID: ";

    private final MaterialRepository materialRepository;
    private final EstadoRepository estadoRepository;
    private final TipoMaterialRepository tipoMaterialRepository;

    @Transactional(readOnly = true)
    public Material findById(Integer id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Transactional
    public Material create(Material material) {
        validarMaterial(material);

        Estado estado = obterEstadoAtivo();
        material.setEstado(estado);

        return materialRepository.save(material);
    }

    @Transactional
    public Material update(Integer id, Material materialAtualizado) {
        Material materialExistente = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id));

        validarMaterial(materialAtualizado);

        atualizarMaterial(materialExistente, materialAtualizado);

        return materialRepository.save(materialExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id);
        }
        materialRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Material findByNomeMaterial(String nomeMaterial) {
        return materialRepository.findByNomeMaterial(nomeMaterial)
                .orElseThrow(() -> new ResourceNotFoundException(NOME_MATERIAL_DUPLICADO_MESSAGE + nomeMaterial));
    }

    private void validarMaterial(Material material) {
        validarNomeMaterialDuplicado(material);
        validarQuantidade(material);
        validarEstado(material);
        validarTipoMaterial(material);
    }

    private void validarNomeMaterialDuplicado(Material material) {
        materialRepository.findByNomeMaterial(material.getNomeMaterial())
                .ifPresent(m -> {
                    if (!m.getId().equals(material.getId())) {
                        throw new BadRequestException(NOME_MATERIAL_DUPLICADO_MESSAGE + material.getNomeMaterial());
                    }
                });
    }

    private void validarQuantidade(Material material) {
        if (material.getQuantidade() != null && material.getQuantidade() < 0) {
            throw new BadRequestException(QUANTIDADE_NEGATIVA_MESSAGE);
        }
    }

    private void validarEstado(Material material) {
        Estado estado = estadoRepository.findById(material.getEstado().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE + material.getEstado().getId()));
        material.setEstado(estado);
    }

    private void validarTipoMaterial(Material material) {
        TipoMaterial tipoMaterial = tipoMaterialRepository.findById(material.getTipoMaterial().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        TIPO_MATERIAL_NOT_FOUND_MESSAGE + material.getTipoMaterial().getId()));
        material.setTipoMaterial(tipoMaterial);
    }

    private void atualizarMaterial(Material materialExistente, Material materialAtualizado) {
        materialExistente.setNomeMaterial(materialAtualizado.getNomeMaterial());
        materialExistente.setQuantidade(materialAtualizado.getQuantidade());
        materialExistente.setTipoMaterial(materialAtualizado.getTipoMaterial());
    }

    private Estado obterEstadoAtivo() {
        return estadoRepository.findEstado("Ativo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));
    }
}
