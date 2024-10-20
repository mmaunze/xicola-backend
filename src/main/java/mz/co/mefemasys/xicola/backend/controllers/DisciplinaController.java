package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Disciplina;
import mz.co.mefemasys.xicola.backend.dto.DisciplinaDTO;
import mz.co.mefemasys.xicola.backend.service.DisciplinaService;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/academico/disciplinas")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @GetMapping

    public ResponseEntity<List<DisciplinaDTO>> findAll() {
        try {
            var comunicados = disciplinaService.findAll();
            var comunicadoList = new ArrayList<Disciplina>();
            comunicados.forEach(comunicadoList::add);

            var comunicadoDTOs = comunicadoList.stream()
                    .map(DisciplinaDTO::new)
                    .collect(toList());
            return new ResponseEntity<>(comunicadoDTOs, OK);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar todos os comunicados", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disciplina/{id}")
    public ResponseEntity<DisciplinaDTO> findById(@PathVariable Long id) {
        try {
            Disciplina disciplina = disciplinaService.findById(id);
            return ResponseEntity.ok(convertToDTO(disciplina));
        } catch (EntityNotFoundException e) {
            log.error("Disciplina não encontrada com o ID: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar disciplina com o ID: {}", id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            Disciplina newDisciplina = disciplinaService.create(convertToEntity(disciplinaDTO));
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newDisciplina.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (InternalServerErrorException e) {
            log.error("Erro ao criar nova disciplina", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaService.update(id, convertToEntity(disciplinaDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Disciplina não encontrada para o ID: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar disciplina com o ID: {}", id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            disciplinaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Disciplina não encontrada para remoção com o ID: {}", id, e);
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover disciplina com o ID: {}", id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    private Disciplina convertToEntity(DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(disciplinaDTO.getId());
        disciplina.setNomeDisciplina(disciplinaDTO.getNome());
        return disciplina;
    }

    private DisciplinaDTO convertToDTO(Disciplina disciplina) {
        return new DisciplinaDTO(disciplina);
    }
}
