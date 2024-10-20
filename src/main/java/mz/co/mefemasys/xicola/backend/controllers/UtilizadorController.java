package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.dto.UtilizadorDTO;
import mz.co.mefemasys.xicola.backend.service.UtilizadorService;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/utilizadores")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class UtilizadorController {

    private final UtilizadorService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UtilizadorDTO>> findAll() {
        try {
            var utilizadores = userService.findAll();
            var utilizadorDTO = utilizadores.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(utilizadorDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos utilizadores", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = userService.count();
        return new ResponseEntity<>(total, OK);
    }

    @GetMapping("/utilizador/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UtilizadorDTO> findUtilizadorById(@PathVariable Long id) {
        try {
            var utilizador = userService.findById(id);
            return ResponseEntity.ok(new UtilizadorDTO(utilizador));
        } catch (EntityNotFoundException e) {
            log.error("Utilizador não encontrado com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar Utilizador com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Utilizador não encontrado para remoção com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover utilizador com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private UtilizadorDTO convertToDTO(Utilizador utilizador) {
        return new UtilizadorDTO(utilizador);
    }

}
