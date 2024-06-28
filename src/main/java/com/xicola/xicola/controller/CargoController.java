package com.xicola.xicola.controller;

import com.xicola.xicola.model.Cargo;
import com.xicola.xicola.model.dto.CargoDTO;
import com.xicola.xicola.service.CargoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administrativo/cargos")
@Slf4j
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoDTO>> findAll() {
        try {
            Iterable<Cargo> cargos = cargoService.findAll();
            List<CargoDTO> cargosDTO = StreamSupport.stream(cargos.spliterator(), false)
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(cargosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os cargos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cargo/{id}")
    public ResponseEntity<CargoDTO> findById(@PathVariable Integer id) {
        try {
            Cargo cargo = cargoService.findById(id);
            return ResponseEntity.ok(new CargoDTO(cargo));
        } catch (EntityNotFoundException e) {
            log.error("Cargo não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar cargo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CargoDTO cargoDTO) {
        try {
            Cargo newCargo = cargoService.create(convertToEntity(cargoDTO));
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newCargo.getId()).toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo cargo", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CargoDTO cargoDTO) {
        try {
            cargoService.update(id, convertToEntity(cargoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Cargo não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar cargo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            cargoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Cargo não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover cargo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Cargo convertToEntity(CargoDTO cargoDTO) {
        Cargo cargo = new Cargo();
        cargo.setId(cargoDTO.getId());
        cargo.setDescricao(cargoDTO.getDescricao());
        return cargo;
    }

    private CargoDTO convertToDTO(Cargo cargo) {
        return new CargoDTO(cargo);
    }
}
