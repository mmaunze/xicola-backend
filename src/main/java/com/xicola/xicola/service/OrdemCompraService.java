package com.xicola.xicola.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Fornecedor;
import com.xicola.xicola.model.Funcionario;
import com.xicola.xicola.model.ItemOrdemCompra;
import com.xicola.xicola.model.OrdemCompra;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.FornecedorRepository;
import com.xicola.xicola.repository.FuncionarioRepository;
import com.xicola.xicola.repository.ItemOrdemCompraRepository;
import com.xicola.xicola.repository.OrdemCompraRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdemCompraService {

    private static final String ORDEM_COMPRA_NOT_FOUND_MESSAGE = "Ordem de compra não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String FORNECEDOR_NOT_FOUND_MESSAGE = "Fornecedor não encontrado com o ID: ";
    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionário não encontrado com o ID: ";

    private final OrdemCompraRepository ordemCompraRepository;
    private final EstadoRepository estadoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ItemOrdemCompraRepository itemOrdemCompraRepository;

    @Transactional(readOnly = true)
    public OrdemCompra findById(Integer id) {
        return ordemCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ORDEM_COMPRA_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<OrdemCompra> findAll() {
        return ordemCompraRepository.findAll();
    }

    @Transactional
    public OrdemCompra create(OrdemCompra ordemCompra) {
        validarOrdemCompra(ordemCompra);

        return ordemCompraRepository.save(ordemCompra);
    }

    @Transactional
    public OrdemCompra update(Integer id, OrdemCompra ordemCompraAtualizada) {
        OrdemCompra ordemCompraExistente = ordemCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ORDEM_COMPRA_NOT_FOUND_MESSAGE + id));

        validarOrdemCompra(ordemCompraAtualizada);

        ordemCompraExistente.setDataOrdem(ordemCompraAtualizada.getDataOrdem());
        ordemCompraExistente.setDataEntrega(ordemCompraAtualizada.getDataEntrega());
        ordemCompraExistente.setValorTotal(ordemCompraAtualizada.getValorTotal());
        ordemCompraExistente.setEstado(ordemCompraAtualizada.getEstado());
        ordemCompraExistente.setFornecedor(ordemCompraAtualizada.getFornecedor());
        ordemCompraExistente.setResponsavel(ordemCompraAtualizada.getResponsavel());

        return ordemCompraRepository.save(ordemCompraExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!ordemCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(ORDEM_COMPRA_NOT_FOUND_MESSAGE + id);
        }
        ordemCompraRepository.deleteById(id);
    }

    @Transactional
    public void adicionarItem(Integer idOrdemCompra, ItemOrdemCompra itemOrdemCompra) {
        OrdemCompra ordemCompra = findById(idOrdemCompra);
        itemOrdemCompra.setOrdemCompra(ordemCompra);

        validarItemOrdemCompra(itemOrdemCompra);

        itemOrdemCompraRepository.save(itemOrdemCompra);
    }

    private void validarOrdemCompra(OrdemCompra ordemCompra) {
        validarDataOrdem(ordemCompra.getDataOrdem());
        validarValorTotal(ordemCompra.getValorTotal());
        validarEstado(ordemCompra);
        validarFornecedor(ordemCompra);
        validarResponsavel(ordemCompra);
    }

    private void validarItemOrdemCompra(ItemOrdemCompra itemOrdemCompra) {
        // Exemplo de validação de item de ordem de compra
        if (itemOrdemCompra.getDescricao() == null || itemOrdemCompra.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do item de ordem de compra não pode estar vazia.");
        }
        if (itemOrdemCompra.getQuantidade() <= 0) {
            throw new BadRequestException("A quantidade do item de ordem de compra deve ser maior que zero.");
        }
        if (itemOrdemCompra.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor unitário do item de ordem de compra deve ser maior que zero.");
        }
    }

    private void validarDataOrdem(@NotNull LocalDate dataOrdem) {
        if (dataOrdem == null) {
            throw new BadRequestException("A data de ordem de compra não pode estar vazia.");
        }
        if (dataOrdem.isAfter(LocalDate.now())) {
            throw new BadRequestException("A data de ordem de compra não pode estar vazia.");
        }

        // Aqui pode ser adicionada validação adicional, por exemplo, a data não pode
        // ser no futuro, etc.
    }

    private void validarValorTotal(BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor total da ordem de compra deve ser maior que zero.");
        }
    }

    private void validarEstado(OrdemCompra ordemCompra) {
        Estado estado = estadoRepository.findById(ordemCompra.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ESTADO_NOT_FOUND_MESSAGE + ordemCompra.getEstado().getId()));
        ordemCompra.setEstado(estado);
    }

    private void validarFornecedor(OrdemCompra ordemCompra) {
        Fornecedor fornecedor = fornecedorRepository.findById(ordemCompra.getFornecedor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        FORNECEDOR_NOT_FOUND_MESSAGE + ordemCompra.getFornecedor().getId()));
        ordemCompra.setFornecedor(fornecedor);
    }

    private void validarResponsavel(OrdemCompra ordemCompra) {
        Funcionario responsavel = funcionarioRepository.findById(ordemCompra.getResponsavel().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        FUNCIONARIO_NOT_FOUND_MESSAGE + ordemCompra.getResponsavel().getId()));
        ordemCompra.setResponsavel(responsavel);
    }
}
