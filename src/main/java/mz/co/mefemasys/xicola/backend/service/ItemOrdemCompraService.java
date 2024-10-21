package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.Estado;

import mz.co.mefemasys.xicola.backend.models.ItemOrdemCompra;

import mz.co.mefemasys.xicola.backend.models.Material;

import mz.co.mefemasys.xicola.backend.models.OrdemCompra;

import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;

import mz.co.mefemasys.xicola.backend.repository.ItemOrdemCompraRepository;

import mz.co.mefemasys.xicola.backend.repository.MaterialRepository;

import mz.co.mefemasys.xicola.backend.repository.OrdemCompraRepository;

import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrdemCompraService implements MetodosGerais {

    private static final String ITEM_ORDEM_COMPRA_NOT_FOUND_MESSAGE = "Item de ordem de compra não encontrado com o ID: ";

    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";

    private static final String QUANTIDADE_INVALIDA_MESSAGE = "A quantidade deve ser maior que zero";

    private static final String VALOR_UNITARIO_INVALIDO_MESSAGE = "O valor unitário deve ser maior que zero";

    private static final String MATERIAL_NOT_FOUND_MESSAGE = "Material não encontrado com o ID: ";

    private static final String ORDEM_COMPRA_NOT_FOUND_MESSAGE = "Ordem de compra não encontrada com o ID: ";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final ItemOrdemCompraRepository itemOrdemCompraRepository;

    private final MaterialRepository materialRepository;

    private final OrdemCompraRepository ordemCompraRepository;

    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public ItemOrdemCompra findById(Long id) {
        return itemOrdemCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ORDEM_COMPRA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<ItemOrdemCompra> findAll() {
        return itemOrdemCompraRepository.findAll();

    }

    @Transactional
    public ItemOrdemCompra create(ItemOrdemCompra itemOrdemCompra) {
        validarItemOrdemCompra(itemOrdemCompra);

        var estado = obterEstadoActivo();

        itemOrdemCompra.setEstado(estado);

        return itemOrdemCompraRepository.save(itemOrdemCompra);

    }

    @Transactional
    public ItemOrdemCompra update(Long id, ItemOrdemCompra itemOrdemCompraAtualizado) {
        var itemOrdemCompraExistente = itemOrdemCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ORDEM_COMPRA_NOT_FOUND_MESSAGE + id));

        validarItemOrdemCompra(itemOrdemCompraAtualizado);

        atualizarItemOrdemCompra(itemOrdemCompraExistente, itemOrdemCompraAtualizado);

        return itemOrdemCompraRepository.save(itemOrdemCompraExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!itemOrdemCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_ORDEM_COMPRA_NOT_FOUND_MESSAGE + id);

        }
        itemOrdemCompraRepository.deleteById(id);

    }

    private void validarItemOrdemCompra(ItemOrdemCompra itemOrdemCompra) {
        validarDadosObrigatorios(itemOrdemCompra);

        validarQuantidade(itemOrdemCompra);

        validarValorUnitario(itemOrdemCompra);

        validarMaterial(itemOrdemCompra);

        validarOrdemCompra(itemOrdemCompra);

    }

    private void validarDadosObrigatorios(ItemOrdemCompra itemOrdemCompra) {
        if (itemOrdemCompra.getDescricao() == null || itemOrdemCompra.getDescricao().isBlank()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);

        }
    }

    private void validarQuantidade(ItemOrdemCompra itemOrdemCompra) {
        if (itemOrdemCompra.getQuantidade() <= 0) {
            throw new BadRequestException(QUANTIDADE_INVALIDA_MESSAGE);

        }
    }

    private void validarValorUnitario(ItemOrdemCompra itemOrdemCompra) {
        if (itemOrdemCompra.getValorUnitario() == null
                || itemOrdemCompra.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(VALOR_UNITARIO_INVALIDO_MESSAGE);

        }
    }

    private void validarMaterial(ItemOrdemCompra itemOrdemCompra) {
        Material material = materialRepository.findById(itemOrdemCompra.getMaterial().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                MATERIAL_NOT_FOUND_MESSAGE + itemOrdemCompra.getMaterial().getId()));

        itemOrdemCompra.setMaterial(material);

    }

    private void validarOrdemCompra(ItemOrdemCompra itemOrdemCompra) {
        OrdemCompra ordemCompra = ordemCompraRepository.findById(itemOrdemCompra.getOrdemCompra().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                ORDEM_COMPRA_NOT_FOUND_MESSAGE + itemOrdemCompra.getOrdemCompra().getId()));

        itemOrdemCompra.setOrdemCompra(ordemCompra);

    }

    private void atualizarItemOrdemCompra(ItemOrdemCompra itemOrdemCompraExistente,
            ItemOrdemCompra itemOrdemCompraAtualizado) {
        itemOrdemCompraExistente.setDescricao(itemOrdemCompraAtualizado.getDescricao());

        itemOrdemCompraExistente.setQuantidade(itemOrdemCompraAtualizado.getQuantidade());

        itemOrdemCompraExistente.setValorUnitario(itemOrdemCompraAtualizado.getValorUnitario());

        itemOrdemCompraExistente.setMaterial(itemOrdemCompraAtualizado.getMaterial());

        itemOrdemCompraExistente.setOrdemCompra(itemOrdemCompraAtualizado.getOrdemCompra());

    }

    private Estado obterEstadoActivo() {
        return estadoRepository.findEstado("Activo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

    }
}
