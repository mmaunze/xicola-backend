package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.AreaCientificaDTO;
import mz.co.mefemasys.xicola.backend.dto.TipoPagamentoDTO;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.TipoPagamento;
import mz.co.mefemasys.xicola.backend.service.AreaCientificaService;
import mz.co.mefemasys.xicola.backend.service.TipoPagamentoService;
import mz.co.mefemasys.xicola.backend.utils.exceptions.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/tipos_pagamento")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class TipoPagamentoController {

    private static final Logger LOG = Logger.getLogger(TipoPagamentoController.class.getName());
    private final TipoPagamentoService tipoPagamentoService;


    @GetMapping
    public ResponseEntity<List<TipoPagamentoDTO>> findAll() {
        try {
            var tipoPagamentos = tipoPagamentoService.findAll();
            var tipoPagamentoDTOS = tipoPagamentos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(tipoPagamentoDTOS, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos tipos de pagamentos", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/area/{id}")
    public ResponseEntity<TipoPagamentoDTO> findById(@PathVariable Long id) {
        try {
            var area = tipoPagamentoService.findById(id);
            return ResponseEntity.ok(new TipoPagamentoDTO(area));

        } catch (EntityNotFoundException e) {
            log.error("Tipo de Pagamento não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar  tipo de pagamento com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TipoPagamento tipoPagamento) {
        try {
            var newTipoPagamento = tipoPagamentoService.create(tipoPagamento);
            var tipoPagamentoDTO = convertToDTO(newTipoPagamento);
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tipoPagamentoDTO.getId())
                    .toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo tipo de pagamento", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody TipoPagamento tipoPagamento) {
        try {
            tipoPagamentoService.update(id, tipoPagamento);

            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Tipo de Pagamento não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar tipo de pagamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tipoPagamentoService.delete(id);

            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Tipo de pagamento não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover tipo de pagamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }


    private TipoPagamentoDTO convertToDTO(TipoPagamento tipoPagamento) {
        return new TipoPagamentoDTO(tipoPagamento);

    }
}
