package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.ContratoDTO;
import mz.co.mefemasys.xicola.backend.models.Contrato;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.service.ContratoService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
import mz.co.mefemasys.xicola.backend.service.FuncionarioService;
import mz.co.mefemasys.xicola.backend.utils.exceptions.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administrativo/contratos")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class ContratoController {

    private static final Logger LOG = Logger.getLogger(ContratoController.class.getName());

    private final ContratoService contratoService;

    private final FuncionarioService funcionarioService;

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<ContratoDTO>> findAll() {
        try {
            var contratos = contratoService.findAll();

            var contratoList = new ArrayList<Contrato>();

            contratos.forEach(contratoList::add);

            var contratoDTOs = contratoList.stream()
                    .map(ContratoDTO::new)
                    .collect(toList());

            return new ResponseEntity<>(contratoDTOs, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos os contratos", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> findById(@PathVariable Long id) {
        try {
            var contrato = contratoService.findById(id);

            return ResponseEntity.ok(new ContratoDTO(contrato));

        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar contrato com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ContratoDTO contratoDTO) {
        try {
            var newContrato = contratoService.create(convertToEntity(contratoDTO));

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newContrato.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Erro ao criar novo contrato", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ContratoDTO contratoDTO) {
        try {
            contratoService.update(id, convertToEntity(contratoDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar contrato com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            contratoService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Contrato não encontrado para remoção com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover contrato com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Contrato convertToEntity(ContratoDTO contratoDTO) {
        var contrato = new Contrato();

        contrato.setId(contratoDTO.getId());

        contrato.setDescricao(contratoDTO.getNome());

        contrato.setTipo(contratoDTO.getTipo());

        contrato.setDataInicio(contratoDTO.getDataInicio());

        contrato.setDataFim(contratoDTO.getDataFim());

        contrato.setValorTotal(contratoDTO.getValorTotal());

        contrato.setFornecedor(contratoDTO.getFornecedor());

        contrato.setResponsavel(fetchResponsavel(contratoDTO.getResponsavel()));

        contrato.setEstado(fetchEstado(contratoDTO.getEstado()));

        return contrato;

    }

    private Funcionario fetchResponsavel(Long id) {
        return funcionarioService.findById(id);

    }

    private Estado fetchEstado(String estado) {
        return estadoService.findEstado(estado);

    }
}
