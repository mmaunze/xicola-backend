package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.AreaCientificaDTO;
import mz.co.mefemasys.xicola.backend.dto.SectorTrabalhoDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;
import mz.co.mefemasys.xicola.backend.service.SectorTrabalhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/sectores_trabalho")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class SectorTranalhoController {

    private final SectorTrabalhoService sectorTrabalhoService;

    @GetMapping
    public ResponseEntity<List<SectorTrabalhoDTO>> findAll() {
        try {
            var sectorTrabalhos = sectorTrabalhoService.findAll();
            var sectoresTrabalho = sectorTrabalhos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(sectoresTrabalho, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos sector de Trabalho ", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area/{id}")
    public ResponseEntity<SectorTrabalhoDTO> findById(@PathVariable Long id) {
        try {
            var sectorTrabalho = sectorTrabalhoService.findById(id);
            return ResponseEntity.ok(new SectorTrabalhoDTO(sectorTrabalho));
        } catch (EntityNotFoundException e) {
            log.error("sector de Trabalho não encontrada com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar sector de Trabalho  com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SectorTrabalhoDTO sectorTrabalhoDTO) {
        try {
            var sectorTrabalho = sectorTrabalhoService.create(convertToEntity(sectorTrabalhoDTO));
            var newSectorTrabalho = convertToDTO(sectorTrabalho);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newSectorTrabalho.getId())
                    .toUri();

            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar nova sector de Trabalho ", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody SectorTrabalhoDTO sectorTrabalhoDTO) {
        try {
            sectorTrabalhoService.update(id, convertToEntity(sectorTrabalhoDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("sector de Trabalho  não encontrada para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar sector de Trabalho  com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            sectorTrabalhoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("sector de Trabalho não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover área científica com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private SectorTrabalho convertToEntity(SectorTrabalhoDTO sectorTrabalhoDTO) {
        var sectorTrabalho = new SectorTrabalho();
        sectorTrabalho.setId(sectorTrabalhoDTO.getId());
        sectorTrabalho.setDescricao(sectorTrabalhoDTO.getNome());
        return sectorTrabalho;
    }

    private SectorTrabalhoDTO convertToDTO(SectorTrabalho sectorTrabalho) {
        return new SectorTrabalhoDTO(sectorTrabalho);
    }
}
