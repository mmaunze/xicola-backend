package com.xicola.xicola.service;

import com.xicola.xicola.model.Ativo;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.repository.AtivoRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtivoService {

    private static final String ATIVO_NOT_FOUND_MESSAGE = "Ativo não encontrado com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";
    private static final String TIPO_VAZIO_MESSAGE = "O tipo não pode estar vazio";
    private static final String DATA_AQUISICAO_VAZIA_MESSAGE = "A data de aquisição não pode estar vazia";
    private static final String VALOR_AQUISICAO_INVALIDO_MESSAGE = "O valor de aquisição é inválido";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final AtivoRepository ativoRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Ativo findById(Integer id) {
        return ativoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ATIVO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Ativo> findAll() {
        return ativoRepository.findAll();
    }

    @Transactional
    public Ativo create(Ativo ativo) {
        // Obtém o estado "Activo" da base de dados
        var estadoOptional = estadoRepository.findEstado("Activo");
        // Verifica se o estado foi encontrado
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        validarDadosObrigatorios(ativo);
        ativo.setEstado(estado);

        return ativoRepository.save(ativo);
    }

    @Transactional
    public Ativo update(Integer id, Ativo ativoAtualizado) {
        var ativoExistente = ativoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ATIVO_NOT_FOUND_MESSAGE + id));

        validarDadosObrigatorios(ativoAtualizado);

        ativoExistente.setDescricao(ativoAtualizado.getDescricao());
        ativoExistente.setTipo(ativoAtualizado.getTipo());
        ativoExistente.setDataAquisicao(ativoAtualizado.getDataAquisicao());
        ativoExistente.setValorAquisicao(ativoAtualizado.getValorAquisicao());
        ativoExistente.setLocalizacao(ativoAtualizado.getLocalizacao());
        ativoExistente.setEstado(ativoAtualizado.getEstado());

        return ativoRepository.save(ativoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!ativoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ATIVO_NOT_FOUND_MESSAGE + id);
        }
        ativoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Ativo ativo) {
        if (ativo.getDescricao() == null || ativo.getDescricao().isBlank()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);
        }
        if (ativo.getTipo() == null || ativo.getTipo().isBlank()) {
            throw new BadRequestException(TIPO_VAZIO_MESSAGE);
        }
        if (ativo.getDataAquisicao() == null) {
            throw new BadRequestException(DATA_AQUISICAO_VAZIA_MESSAGE);
        }
        if (ativo.getValorAquisicao() == null || ativo.getValorAquisicao().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(VALOR_AQUISICAO_INVALIDO_MESSAGE);
        }
    }
}
