package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Ativo;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.dto.AtivoDTO;
import mz.co.mefemasys.xicola.backend.service.AtivoService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
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
@RequestMapping("/aquisicao/ativos")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class AtivoController {

    private final EstadoService estadoService;
    private final AtivoService ativoService;

    @GetMapping
    public ResponseEntity<List<AtivoDTO>> findAll() {
        try {
            var ativos = ativoService.findAll();
            var ativosDTO = ativos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(ativosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os ativos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoDTO> findById(@PathVariable Long id) {
        try {
            Ativo ativo = ativoService.findById(id);
            return ResponseEntity.ok(new AtivoDTO(ativo));
        } catch (EntityNotFoundException e) {
            log.error("Ativo não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar ativo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AtivoDTO ativoDTO) {
        try {
            var newAtivo = ativoService.create(convertToEntity(ativoDTO));
            var newAtivoDTO = convertToDTO(newAtivo);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAtivoDTO.getId())
                    .toUri();

            return created(location).build();
        } catch (EntityNotFoundException e) {
            log.error("Estado não encontrado: " + ativoDTO.getEstado(), e);
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (Exception e) {
            log.error("Erro ao criar novo ativo", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AtivoDTO ativoDTO) {
        try {
            ativoService.update(id, convertToEntity(ativoDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Ativo ou Estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar ativo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ativoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Ativo não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover ativo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Ativo convertToEntity(AtivoDTO ativoDTO) {
        Ativo ativo = new Ativo();
        ativo.setId(ativoDTO.getId());
        ativo.setDescricao(ativoDTO.getDescricao());
        ativo.setTipo(ativoDTO.getTipo());
        ativo.setDataAquisicao(ativoDTO.getDataAquisicao());
        ativo.setValorAquisicao(ativoDTO.getValorAquisicao());
        ativo.setLocalizacao(ativoDTO.getLocalizacao());
        Estado estado = estadoService.findEstado(ativoDTO.getEstado());
        ativo.setEstado(estado);

        return ativo;
    }

    private AtivoDTO convertToDTO(Ativo ativo) {
        return new AtivoDTO(ativo);
    }
}
