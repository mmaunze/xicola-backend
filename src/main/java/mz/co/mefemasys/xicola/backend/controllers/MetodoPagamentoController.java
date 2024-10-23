package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.MetodoPagamentoDTO;
import mz.co.mefemasys.xicola.backend.dto.TipoPagamentoDTO;
import mz.co.mefemasys.xicola.backend.models.MetodoPagamento;
import mz.co.mefemasys.xicola.backend.models.TipoPagamento;
import mz.co.mefemasys.xicola.backend.service.MetodoPagamentoService;
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
@RequestMapping("/metodos_pagamento")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class MetodoPagamentoController {

    private static final Logger LOG = Logger.getLogger(MetodoPagamentoController.class.getName());
    private final MetodoPagamentoService metodoPagamentoService;


    @GetMapping
    public ResponseEntity<List<MetodoPagamentoDTO>> findAll() {
        try {
            var metodoPagamentos = metodoPagamentoService.findAll();
            var metodoPagamentoDTOS = metodoPagamentos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(metodoPagamentoDTOS, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos metodos de pagamentos", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/metodo/{id}")
    public ResponseEntity<MetodoPagamentoDTO> findById(@PathVariable Long id) {
        try {
            var metodoPagamento = metodoPagamentoService.findById(id);
            return ResponseEntity.ok(new MetodoPagamentoDTO(metodoPagamento));

        } catch (EntityNotFoundException e) {
            log.error("Metodo de Pagamento não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar  Metodo de pagamento com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MetodoPagamentoDTO metodoPagamento) {
        try {
            var newMetodoPagamento = metodoPagamentoService.create(metodoPagamento);
            var metodoPagamentoDTO = convertToDTO(newMetodoPagamento);
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(metodoPagamentoDTO.getId())
                    .toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo metodo de pagamento", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MetodoPagamentoDTO tipoPagamento) {
        try {
            metodoPagamentoService.update(id, tipoPagamento);
            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Metodo de Pagamento não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar metodo de pagamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            metodoPagamentoService.delete(id);

            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Metodo de pagamento não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover metdo de pagamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }


    private MetodoPagamentoDTO convertToDTO(MetodoPagamento metodoPagamento) {
        return new MetodoPagamentoDTO(metodoPagamento);

    }
}
