package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.models.dto.AlunoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.models.dto.UtilizadorDTO;
import mz.co.mefemasys.xicola.backend.service.UtilizadorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/utilizadores")
@RequiredArgsConstructor
@Slf4j
public class UtilizadorController {

    private final UtilizadorService userService;

    @GetMapping
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

    @GetMapping("/utilizador/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_PEDAGOGICO') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<UtilizadorDTO> findUtilizadorById(@PathVariable Long id) {
        try {
            var utilizador = userService.findById(id);
            return ResponseEntity.ok(new UtilizadorDTO(utilizador));
        } catch (EntityNotFoundException e) {
            log.error("Utilizador não encontrado com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar Utilizador com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/create/user")
    public ResponseEntity<Void> createUser(@RequestBody Utilizador utilizador) {
        userService.create(utilizador);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Utilizador não encontrado para remoção com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover utilizador com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private UtilizadorDTO convertToDTO(Utilizador utilizador) {
        return new UtilizadorDTO(utilizador);
    }

}