package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.DepartamentoDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Departamento;
import mz.co.mefemasys.xicola.backend.service.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departamentos")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class DepartamentoController {

    private static final Logger LOG = Logger.getLogger(DepartamentoController.class.getName());

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
    public ResponseEntity<DepartamentoDTO> findById(@PathVariable Long id) {
        try {
            var departamento = departamentoService.findById(id);

            return ResponseEntity.ok(new DepartamentoDTO(departamento));

        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar departamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/sigla/{sigla}")
    public ResponseEntity<DepartamentoDTO> findBySigla(@PathVariable String sigla) {
        try {
            var departamento = departamentoService.findBySigla(sigla);

            return ResponseEntity.ok(new DepartamentoDTO(departamento));

        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado com o ID: " + sigla, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar departamento com o ID: " + sigla, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> create(@RequestBody Departamento departamento) {
        try {

            var newDepartamento = departamentoService.create(departamento);

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
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Departamento departamento) {
        try {
            departamentoService.update(id, departamento);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar departamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            departamentoService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Departamento não encontrado para remoção com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover departamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Departamento convertToEntity(DepartamentoDTO departamentoDTO) {
        var departamento = new Departamento();

        departamento.setId(departamentoDTO.getId());

        departamento.setDescricao(departamentoDTO.getNome());

        departamento.setSigla(departamentoDTO.getSigla());

        return departamento;

    }
}
