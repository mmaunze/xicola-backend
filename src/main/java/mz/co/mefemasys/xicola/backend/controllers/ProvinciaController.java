package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.ProvinciaDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Provincia;
import mz.co.mefemasys.xicola.backend.service.ProvinciaService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/provincias")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class ProvinciaController {

    private static final Logger LOG = Logger.getLogger(ProvinciaController.class.getName());

    private final ProvinciaService provinciaService;

    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> findAll() {
        try {
            var provincias = provinciaService.findAll();

            var provinciaArrayList = new ArrayList<Provincia>();

            provincias.forEach(provinciaArrayList::add);

            var provinciaDTOS = provinciaArrayList.stream()
                    .map(ProvinciaDTO::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(provinciaDTOS, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar provincias", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> findById(@PathVariable Long id) {
        try {
            var distrito = provinciaService.findById(id);

            return ResponseEntity.ok(new ProvinciaDTO(distrito));

        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar distrito com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProvinciaDTO provinciaDTO) {
        try {
            var provincia = provinciaService.create(convertToEntity(provinciaDTO));

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(provincia.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Erro ao criar novo distrito", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProvinciaDTO provinciaDTO) {
        try {
            provinciaService.update(id, convertToEntity(provinciaDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar distrito com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            provinciaService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("distrito não encontrado para remoção com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover distrito com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Provincia convertToEntity(ProvinciaDTO provinciaDTO) {
        var provincia = new Provincia();

        provincia.setId(provinciaDTO.getId());

        provincia.setNomeProvincia(provinciaDTO.getNome());

        return provincia;

    }

    private Provincia fectchProvincia(String provincia) {

        return provinciaService.findProvincia(provincia);

    }
}
