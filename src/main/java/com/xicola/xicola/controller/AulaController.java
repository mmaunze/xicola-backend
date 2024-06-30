package com.xicola.xicola.controller;

import com.xicola.xicola.model.Aula;
import com.xicola.xicola.model.dto.AulaDTO;
import com.xicola.xicola.service.AulaService;
import com.xicola.xicola.service.DisciplinaService;
import com.xicola.xicola.service.EstadoService;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/academico/aulas")
@Slf4j
public class AulaController {

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
    public ResponseEntity<AulaDTO> findById(@PathVariable Integer id) {
        try {
            Aula aula = aulaService.findById(id);
            return ResponseEntity.ok(new AulaDTO(aula));
        } catch (EntityNotFoundException e) {
            log.error("Aula não encontrada com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
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
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody AulaDTO aulaDTO) {
        try {
            aulaService.update(id, convertToEntity(aulaDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Aula, Disciplina ou Estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar aula com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            aulaService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Aula não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
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

        aula.setDisciplina(disciplinaService.findDisciplina(aulaDTO.getDisciplina())
                .orElseThrow(
                        () -> new EntityNotFoundException("Disciplina não encontrada: " + aulaDTO.getDisciplina())));
        aula.setEstado(estadoService.findEstado(aulaDTO.getEstado())
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado: " + aulaDTO.getEstado())));

        return aula;
    }

    private AulaDTO convertToDTO(Aula aula) {
        return new AulaDTO(aula);
    }
}
