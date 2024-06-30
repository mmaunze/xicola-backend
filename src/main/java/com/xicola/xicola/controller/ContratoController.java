package com.xicola.xicola.controller;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xicola.xicola.model.Contrato;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Funcionario;
import com.xicola.xicola.model.dto.ContratoDTO;
import com.xicola.xicola.service.ContratoService;
import com.xicola.xicola.service.EstadoService;
import com.xicola.xicola.service.FuncionarioService;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administrativo/contratos")
@Slf4j
public class ContratoController {

    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<ContratoDTO>> findAll() {
        try {
            var contratos = contratoService.findAll();
            var contratoList = new ArrayList<Contrato>();
            contratos.forEach(contratoList::add);

            var contratoDTOs = contratoList.stream()
                    .map(ContratoDTO::new)
                    .collect(toList());
            return new ResponseEntity<>(contratoDTOs, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os contratos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> findById(@PathVariable Integer id) {
        try {
            var contrato = contratoService.findById(id);
            return ResponseEntity.ok(new ContratoDTO(contrato));
        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar contrato com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ContratoDTO contratoDTO) {
        try {
            var newContrato = contratoService.create(convertToEntity(contratoDTO));
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newContrato.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo contrato", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ContratoDTO contratoDTO) {
        try {
            contratoService.update(id, convertToEntity(contratoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar contrato com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            contratoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover contrato com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Contrato convertToEntity(ContratoDTO contratoDTO) {
        var contrato = new Contrato();
        contrato.setId(contratoDTO.getId());
        contrato.setDescricao(contratoDTO.getNome());
        contrato.setTipo(contratoDTO.getTipo());
        contrato.setDataInicio(contratoDTO.getDataInicio());
        contrato.setDataFim(contratoDTO.getDataFim());
        contrato.setValorTotal(contratoDTO.getValorTotal());
        contrato.setFornecedor(contratoDTO.getFornecedor());

        contrato.setResponsavel(fetchResponsavel(contratoDTO.getResponsavel()));
        contrato.setEstado(fetchEstado(contratoDTO.getEstado()));

        return contrato;
    }

    private Funcionario fetchResponsavel(Long id) {
        return funcionarioService.findById(id);
    }

    private Estado fetchEstado(String estado) {
        return estadoService.findEstado(estado)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));
    }
}
