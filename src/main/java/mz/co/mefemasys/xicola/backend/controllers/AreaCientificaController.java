package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.dto.AreaCientificaDTO;
import mz.co.mefemasys.xicola.backend.service.AreaCientificaService;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/areas-cientificas")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class AreaCientificaController {

    private final AreaCientificaService areaCientificaService;

    @GetMapping
    public ResponseEntity<List<AreaCientificaDTO>> findAll() {
        try {
            var areas = areaCientificaService.findAll();
            var areasDTO = areas.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(areasDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todas as áreas científicas", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area/{id}")
    public ResponseEntity<AreaCientificaDTO> findById(@PathVariable Long id) {
        try {
            var area = areaCientificaService.findById(id);
            return ResponseEntity.ok(new AreaCientificaDTO(area));
        } catch (EntityNotFoundException e) {
            log.error("Área científica não encontrada com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar área científica com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AreaCientificaDTO areaCientificaDTO) {
        try {
            var newArea = areaCientificaService.create(convertToEntity(areaCientificaDTO));
            var newAreaDTO = convertToDTO(newArea);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAreaDTO.getId())
                    .toUri();

            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar nova área científica", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AreaCientificaDTO areaCientificaDTO) {
        try {
            areaCientificaService.update(id, convertToEntity(areaCientificaDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Área científica não encontrada para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar área científica com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            areaCientificaService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Área científica não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover área científica com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private AreaCientifica convertToEntity(AreaCientificaDTO areaCientificaDTO) {
        var area = new AreaCientifica();
        area.setId(areaCientificaDTO.getId());
        area.setDescricao(areaCientificaDTO.getNome());
        return area;
    }

    private AreaCientificaDTO convertToDTO(AreaCientifica area) {
        return new AreaCientificaDTO(area);
    }
}
