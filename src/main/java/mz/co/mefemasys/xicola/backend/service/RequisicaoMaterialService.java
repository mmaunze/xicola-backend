package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.RequisicaoMaterial;

import mz.co.mefemasys.xicola.backend.repository.RequisicaoMaterialRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.List;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RequisicaoMaterialService {

    private static final String REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE = "Requisição de material não encontrada com o ID: ";

    private static final Logger LOG = Logger.getLogger(RequisicaoMaterialService.class.getName());

    private final RequisicaoMaterialRepository requisicaoMaterialRepository;

    @Transactional(readOnly = true)
    public RequisicaoMaterial findById(Long id) {
        return requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterial> findAll() {
        return requisicaoMaterialRepository.findAll();

    }

    @Transactional
    public RequisicaoMaterial create(RequisicaoMaterial requisicaoMaterial) {
        validarRequisicaoMaterial(requisicaoMaterial);

        return requisicaoMaterialRepository.save(requisicaoMaterial);

    }

    @Transactional
    public RequisicaoMaterial update(Long id, RequisicaoMaterial requisicaoMaterialAtualizada) {
        var requisicaoMaterialExistente = requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));

        validarRequisicaoMaterial(requisicaoMaterialAtualizada);

        requisicaoMaterialExistente.setDataRequisicao(requisicaoMaterialAtualizada.getDataRequisicao());

        requisicaoMaterialExistente.setQuantidade(requisicaoMaterialAtualizada.getQuantidade());

        requisicaoMaterialExistente.setObservacao(requisicaoMaterialAtualizada.getObservacao());

        requisicaoMaterialExistente.setEstado(requisicaoMaterialAtualizada.getEstado());

        requisicaoMaterialExistente.setRequisitor(requisicaoMaterialAtualizada.getRequisitor());

        requisicaoMaterialExistente.setMaterial(requisicaoMaterialAtualizada.getMaterial());

        return requisicaoMaterialRepository.save(requisicaoMaterialExistente);

    }

    @Transactional
    public void delete(Long id) {
        var requisicaoMaterial = requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));

        requisicaoMaterialRepository.delete(requisicaoMaterial);

    }

    private void validarRequisicaoMaterial(RequisicaoMaterial requisicaoMaterial) {
        if (requisicaoMaterial.getDataRequisicao() == null
                || requisicaoMaterial.getDataRequisicao().isAfter(new Date().toInstant())) {
            throw new BadRequestException(
                    "A data de requisição do material não pode ser nula e deve estar no passado.");

        }

        if (requisicaoMaterial.getQuantidade() <= 0) {
            throw new BadRequestException("A quantidade requisitada deve ser maior que zero.");

        }

        // Adicione outras validações conforme necessário
    }
}
