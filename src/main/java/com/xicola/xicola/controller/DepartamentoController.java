package com.xicola.xicola.controller;

import com.xicola.xicola.model.Departamento;
import com.xicola.xicola.model.dto.DepartamentoDTO;
import com.xicola.xicola.service.DepartamentoService;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geral/departamentos")
@Slf4j
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> findAll() {
        try {
            var departamentos = departamentoService.findAll();
            var departamentoList = new ArrayList<Departamento>();
            departamentos.forEach(departamentoList::add);

            var departamentoDTOs = departamentoList.stream()
                    .map(DepartamentoDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(departamentoDTOs, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os departamentos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> findById(@PathVariable Integer id) {
        try {
            var departamento = departamentoService.findById(id);
            return ResponseEntity.ok(new DepartamentoDTO(departamento));
        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar departamento com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/departamento/{sigla}")
    public ResponseEntity<DepartamentoDTO> findBySigla(@PathVariable String sigla) {
        try {
            var departamento = departamentoService.findBySigla(sigla);
            return ResponseEntity.ok(new DepartamentoDTO(departamento));
        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado com o ID: " + sigla, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar departamento com o ID: " + sigla, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DepartamentoDTO departamentoDTO) {
        try {
            var newDepartamento = departamentoService.create(convertToEntity(departamentoDTO));
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newDepartamento.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo departamento", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            departamentoService.update(id, convertToEntity(departamentoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar departamento com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            departamentoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover departamento com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Departamento convertToEntity(DepartamentoDTO departamentoDTO) {
        var departamento = new Departamento();
        departamento.setId(departamentoDTO.getId());
        departamento.setDescricao(departamentoDTO.getDescricao());
        departamento.setSigla(departamentoDTO.getSigla());
        return departamento;
    }
}
