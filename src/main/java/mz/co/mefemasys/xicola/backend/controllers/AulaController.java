package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.AulaDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Aula;
import mz.co.mefemasys.xicola.backend.service.AulaService;
import mz.co.mefemasys.xicola.backend.service.DisciplinaService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
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
@RequestMapping("/academico/aulas")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class AulaController {

    private static final Logger LOG = Logger.getLogger(AulaController.class.getName());

    private final DisciplinaService disciplinaService;

    private final EstadoService estadoService;

    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDTO>> findAll() {
        try {
            var aulas = aulaService.findAll();

            var aulasDTO = aulas.stream()
                    .map(this::convertToDTO)
                    .collect(toList());

            return new ResponseEntity<>(aulasDTO, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todas as aulas", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> findById(@PathVariable Long id) {
        try {
            Aula aula = aulaService.findById(id);

            return ResponseEntity.ok(new AulaDTO(aula));

        } catch (EntityNotFoundException e) {
            log.error("Aula não encontrada com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar aula com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AulaDTO aulaDTO) {
        try {
            var newAula = aulaService.create(convertToEntity(aulaDTO));

            var newAulaDTO = convertToDTO(newAula);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAulaDTO.getId())
                    .toUri();

            return created(location).build();

        } catch (EntityNotFoundException e) {
            log.error("Disciplina ou Estado não encontrado: " + aulaDTO.getDisciplina() + " ou " + aulaDTO.getEstado(),
                    e);

            return new ResponseEntity<>(BAD_REQUEST);

        } catch (Exception e) {
            log.error("Erro ao criar nova aula", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        try {
            aulaService.update(id, convertToEntity(aulaDTO));

            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Aula, Disciplina ou Estado não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar aula com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            aulaService.delete(id);

            return ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Aula não encontrada para remoção com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover aula com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Aula convertToEntity(AulaDTO aulaDTO) {
        Aula aula = new Aula();

        aula.setId(aulaDTO.getId());

        aula.setTitulo(aulaDTO.getTitulo());

        aula.setAnoLectivo(aulaDTO.getAnoLectivo());

        aula.setClasse(aulaDTO.getClasse());

        aula.setResumo(aulaDTO.getResumo());

        aula.setDataAula(aulaDTO.getDataAula());

        aula.setConteudo(aulaDTO.getConteudo());

        aula.setDisciplina(disciplinaService.findDisciplina(aulaDTO.getDisciplina()));

        aula.setEstado(estadoService.findEstado(aulaDTO.getEstado()));

        return aula;

    }

    private AulaDTO convertToDTO(Aula aula) {
        return new AulaDTO(aula);

    }
}
