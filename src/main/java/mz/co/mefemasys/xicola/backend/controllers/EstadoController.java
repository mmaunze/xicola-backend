package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.dto.EstadoDTO;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.created;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geral/estados")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        try {
            Iterable<Estado> estados = estadoService.findAll();
            List<EstadoDTO> estadosDTO = StreamSupport.stream(estados.spliterator(), false)
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(estadosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os estados", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tipo_estado/{tipo}")
    public ResponseEntity<List<EstadoDTO>> findTipoEstado(@PathVariable String tipo) {
        try {
            Iterable<Estado> estados = estadoService.findEstadoTipo(tipo);
            List<EstadoDTO> estadosDTO = StreamSupport.stream(estados.spliterator(), false)
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(estadosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os estados", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/estado/{id}")
    public ResponseEntity<EstadoDTO> findById(@PathVariable Long id) {
        try {
            Estado estado = estadoService.findById(id);
            return ResponseEntity.ok(new EstadoDTO(estado));
        } catch (EntityNotFoundException e) {
            log.error("Estado não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar estado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody EstadoDTO estadoDTO) {
        try {
            Estado newEstado = estadoService.create(convertToEntity(estadoDTO));
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newEstado.getId()).toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo estado", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody EstadoDTO estadoDTO) {
        try {
            estadoService.update(id, convertToEntity(estadoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar estado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            estadoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Estado não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover estado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Estado convertToEntity(EstadoDTO estadoDTO) {
        Estado estado = new Estado();
        estado.setId(estadoDTO.getId());
        estado.setDescricao(estadoDTO.getDescricao());
        return estado;
    }

    private EstadoDTO convertToDTO(Estado estado) {
        return new EstadoDTO(estado);
    }
}
