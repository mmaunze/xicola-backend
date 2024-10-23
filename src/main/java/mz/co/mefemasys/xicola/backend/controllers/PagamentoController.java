package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.PagamentoDTO;
import mz.co.mefemasys.xicola.backend.dto.create.CreatePagamentoDTO;
import mz.co.mefemasys.xicola.backend.models.Pagamento;
import mz.co.mefemasys.xicola.backend.service.PagamentoService;
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
@RequestMapping("/pagamentos")
@Slf4j
@PreAuthorize("hasRole('FINANCEIRO') or hasRole('ADMIN')")
public class PagamentoController implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(PagamentoController.class.getName());

    private final PagamentoService pagamentoService;

    @GetMapping

    public ResponseEntity<List<PagamentoDTO>> findAll() {
        try {
            var pagamentos = pagamentoService.findAll();
            var pagamentosDTO = pagamentos.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(pagamentosDTO, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos pagamentos", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/pagamento/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN') or hasRole('FINANCEIRO')")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id) {
        try {
            var pagamento = pagamentoService.findById(id);
            return ResponseEntity.ok(new PagamentoDTO(pagamento));
        } catch (EntityNotFoundException e) {
            log.error("Pagamento não encontrado com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar Pagamento com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = pagamentoService.count();
        return new ResponseEntity<>(total, OK);

    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Long> totalEstado(@PathVariable String estado) {
        var total = pagamentoService.totalEstado(estado);

        return new ResponseEntity<>(total, OK);

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Long> totalTipo(@PathVariable String tipo) {
        var total = pagamentoService.totalTipo(tipo);
        return new ResponseEntity<>(total, OK);
    }


    @PostMapping("/registar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCEIRO')")
    public ResponseEntity<Void> create(@RequestBody CreatePagamentoDTO pagamento) {
        try {
            var newPagamento = pagamentoService.create(pagamento);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newPagamento.getId())
                    .toUri();

            return created(location).build();

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(BAD_REQUEST);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao registar novo pagamento", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCEIRO')")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody Pagamento pagamento) {
        try {
            pagamentoService.update(id, pagamento);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Pagamento não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar pagamento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            pagamentoService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Pagamento não encontrado para remoção com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover pagamento com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }



    private PagamentoDTO convertToDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento);

    }



}
