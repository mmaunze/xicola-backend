package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.TipoMaterial;
import mz.co.mefemasys.xicola.backend.repository.TipoMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoMaterialService {

    private static final String TIPO_MATERIAL_NOT_FOUND_MESSAGE = "Tipo de material não encontrado com o ID: ";

    private final TipoMaterialRepository tipoMaterialRepository;

    @Transactional(readOnly = true)
    public TipoMaterial findById(Long id) {
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
    public TipoMaterial update(Long id, TipoMaterial tipoMaterialAtualizado) {
        var tipoMaterialExistente = tipoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIPO_MATERIAL_NOT_FOUND_MESSAGE + id));

        validarTipoMaterial(tipoMaterialAtualizado);

        tipoMaterialExistente.setDescricao(tipoMaterialAtualizado.getDescricao());

        return tipoMaterialRepository.save(tipoMaterialExistente);
    }

    @Transactional
    public void delete(Long id) {
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
