package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.FuncionarioDTO;
import mz.co.mefemasys.xicola.backend.dto.create.CreateFuncionarioDTO;
import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.service.*;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import mz.co.mefemasys.xicola.backend.utils.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/funcionarios")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class FuncionarioController implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(FuncionarioController.class.getName());

    private final FuncionarioService funcionarioService;

    private final UtilizadorService utilizadorService;

    private final SectorTrabalhoService sectorTrabalhoService;

    private final DistritoService distritoService;

    private final EstadoService estadoService;

    private final AreaCientificaService areaCientificaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        try {
            var funcionarios = funcionarioService.findAll();

            var funcionarioDTOS = funcionarios.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(funcionarioDTOS, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos os funcionarios", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/funcionario/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
        try {
            var funcionario = funcionarioService.findById(id);

            return ResponseEntity.ok(new FuncionarioDTO(funcionario));

        } catch (EntityNotFoundException e) {
            log.error("Funcionario não encontrado com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar Funcionario com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = funcionarioService.count();

        return new ResponseEntity<>(total, OK);

    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Long> totalEstado(@PathVariable String estado) {
        var total = funcionarioService.totalEstado(estado);

        return new ResponseEntity<>(total, OK);

    }

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody CreateFuncionarioDTO funcionario) {
        try {
            var newFuncionario = funcionarioService.create(funcionario);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newFuncionario.getId())
                    .toUri();

            return created(location).build();

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(BAD_REQUEST);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao criar novo funcionario", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            funcionarioService.update(id, convertToEntity(funcionarioDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Funcionario não encontrado para o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar funcionario com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            funcionarioService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Funcionario não encontrado para remoção com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover funcionario com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Funcionario convertToEntity(FuncionarioDTO funcionarioDTO) {

        return new Funcionario();

    }

    private FuncionarioDTO convertToDTO(Funcionario funcionario) {
        return new FuncionarioDTO(funcionario);

    }


}
