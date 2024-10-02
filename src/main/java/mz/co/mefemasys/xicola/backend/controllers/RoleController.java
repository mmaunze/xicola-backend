package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.models.Cargo;
import mz.co.mefemasys.xicola.backend.models.ERole;
import mz.co.mefemasys.xicola.backend.models.Role;
import mz.co.mefemasys.xicola.backend.models.dto.CargoDTO;
import mz.co.mefemasys.xicola.backend.models.dto.RoleDTO;
import mz.co.mefemasys.xicola.backend.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/roles")
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {
        try {
            Iterable<Role> roles = roleService.findAll();
            List<RoleDTO> cargosDTO = StreamSupport.stream(roles.spliterator(), false)
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(cargosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os cargos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
        try {
            Role role = roleService.findById(id);
            return ResponseEntity.ok(new RoleDTO(role));
        } catch (EntityNotFoundException e) {
            log.error("Role não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar role com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RoleDTO roleDTO) {
        try {
            Role newRole = roleService.create(convertToEntity(roleDTO));
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newRole.getId()).toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo cargo", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        try {
            roleService.update(id, convertToEntity(roleDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Cargo não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar cargo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            roleService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Cargo não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover cargo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(ERole.valueOf(roleDTO.getName()));
        return role;
    }

    private RoleDTO convertToDTO(Role role) {
        return new RoleDTO(role);
    }
}
