package mz.co.mefemasys.xicola.backend.controllers;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import mz.co.mefemasys.xicola.backend.models.Distrito;
import mz.co.mefemasys.xicola.backend.models.Provincia;
import mz.co.mefemasys.xicola.backend.models.dto.DistritoDTO;
import mz.co.mefemasys.xicola.backend.service.DistritoService;
import mz.co.mefemasys.xicola.backend.service.ProvinciaService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geral/distritos")
@Slf4j
public class DistritoController {

    private final DistritoService distritoService;
    private final ProvinciaService provinciaService;

    @GetMapping
    public ResponseEntity<List<DistritoDTO>> findAll() {
        try {
            var distritos = distritoService.findAll();
            var distritoList = new ArrayList<Distrito>();
            distritos.forEach(distritoList::add);

            var distritoDTOs = distritoList.stream()
                    .map(DistritoDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(distritoDTOs, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os distritos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/provincia/{provincia}")
    public ResponseEntity<List<DistritoDTO>> findByProvincia(@RequestParam String provincia) {
        try {
            var distritos = distritoService.findDistritoProvincia(provincia) ;
            var distritoList = new ArrayList<Distrito>();
            distritoList.addAll(distritos);

            var distritoDTOs = distritoList.stream()
                    .map(DistritoDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(distritoDTOs, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar os distritos da provincia ", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<DistritoDTO> findById(@PathVariable Long id) {
        try {
            var distrito = distritoService.findById(id);
            return ResponseEntity.ok(new DistritoDTO(distrito));
        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar distrito com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DistritoDTO distritoDTO) {
        try {
            var newdistrito = distritoService.create(convertToEntity(distritoDTO));
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newdistrito.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo distrito", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DistritoDTO distritoDTO) {
        try {
            distritoService.update(id, convertToEntity(distritoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar distrito com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            distritoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover distrito com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Distrito convertToEntity(DistritoDTO distritoDTO) {
        var distrito = new Distrito();
        distrito.setId(distritoDTO.getId());
        distrito.setNomeDistrito(distritoDTO.getNome());
        distrito.setProvincia(fectchProvincia(distritoDTO.getProvincia()));
        return distrito;
    }

    private Provincia fectchProvincia(String provincia) {
        return provinciaService.findProvincia(provincia);
    }
}
