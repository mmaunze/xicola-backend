package mz.co.mefemasys.xicola.backend.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.ProfessorDTO;
import mz.co.mefemasys.xicola.backend.dto.create.CreateProfessorDTO;
import mz.co.mefemasys.xicola.backend.models.Distrito;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Professor;
import mz.co.mefemasys.xicola.backend.service.DistritoService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
import mz.co.mefemasys.xicola.backend.service.ProfessorService;
import mz.co.mefemasys.xicola.backend.service.ProvinciaService;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import mz.co.mefemasys.xicola.backend.utils.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/professores")
@Slf4j
@PreAuthorize("hasRole('ADMIN') or hasRole('PEDAGOGICO')")
public class ProfessorController implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(ProfessorController.class.getName());

    private final EstadoService estadoService;

    private final ProfessorService professorService;

    private final ProvinciaService provinciaService;

    private final DistritoService distritoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PEDAGOGICO')")
    public ResponseEntity<List<ProfessorDTO>> findAll() {
        try {
            var professors = professorService.findAll();

            var professorDTOS = professors.stream()
                    .map(this::convertToDTO)
                    .collect(toList());

            return new ResponseEntity<>(professorDTOS, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos os professores", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/professor/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN') or hasRole('PEDAGOGICO')")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable Long id) {
        try {
            var professor = professorService.findById(id);

            return ResponseEntity.ok(new ProfessorDTO(professor));

        } catch (ResourceNotFoundException e) {
            log.error("Professor não encontrado com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar professor com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = professorService.count();

        return new ResponseEntity<>(total, OK);

    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Long> totalEstado(@PathVariable String estado) {
        var total = professorService.totalEstado(estado);

        return new ResponseEntity<>(total, OK);

    }

    @PostMapping("/cadastrar")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody CreateProfessorDTO professor) {
        try {
            var newProfessor = professorService.create(professor);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newProfessor.getId())
                    .toUri();

            return created(location).build();

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(BAD_REQUEST);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao criar novo professor", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO) {
        try {
            professorService.update(id, convertToEntity(professorDTO));

            return ok().build();

        } catch (ResourceNotFoundException e) {
            log.error("professor ou Estado não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar professor com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            professorService.delete(id);

            return ok().build();

        } catch (ResourceNotFoundException e) {
            log.error("professr não encontrado para remoção com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover professor com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Professor convertToEntity(ProfessorDTO professorDTO) {
        var professor = new Professor();

        professor.setId(professorDTO.getId());

        professor.setNomeCompleto(professorDTO.getNomeCompleto());

        professor.setDataNascimento(converterStringParaData(professorDTO.getDataNascimento()));

        professor.setDistritoNascimento(fectchDistrito(professorDTO.getDistritoNascimento()));

        professor.setSexo(professorDTO.getSexo());

        professor.setBilheteIdentificacao(professorDTO.getBilheteIdentificacao());

        professor.setReligiao(professorDTO.getReligiao());

        professor.setGrupoSanguineo(professorDTO.getGrupoSanguineo());

        professor.setEndereco(professorDTO.getEndereco());

        professor.setDataContracto(professorDTO.getDataContracto());

        Estado estado = estadoService.findEstado(professorDTO.getEstado());

        professor.setEstado(estado);

        professor.setEscolaAnterior(professorDTO.getEscolaAnterior());

        professor.setNomeDoPai(professorDTO.getNomeDoPai());

        professor.setNomeDaMae(professorDTO.getNomeDaMae());

        professor.setNumeroTelefonePrincipal(professorDTO.getNumeroTelefonePrincipal());

        return professor;

    }

    private ProfessorDTO convertToDTO(Professor professor) {
        return new ProfessorDTO(professor);

    }

    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);

    }

}
