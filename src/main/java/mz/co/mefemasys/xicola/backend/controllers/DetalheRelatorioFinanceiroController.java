package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import mz.co.mefemasys.xicola.backend.dto.DetalheRelatorioFinanceiroDTO;

import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;

import mz.co.mefemasys.xicola.backend.models.DetalheRelatorioFinanceiro;

import mz.co.mefemasys.xicola.backend.models.RelatorioFinanceiro;

import mz.co.mefemasys.xicola.backend.service.DetalheRelatorioFinanceiroService;

import mz.co.mefemasys.xicola.backend.service.RelatorioFinanceiroService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.util.ArrayList;

import java.util.List;

import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financeiro/relatorios/detalhes")
@PreAuthorize("isFullyAuthenticated()")
public class DetalheRelatorioFinanceiroController {

    private static final Logger LOG = Logger.getLogger(DetalheRelatorioFinanceiroController.class.getName());

    private final DetalheRelatorioFinanceiroService detalheRelatorioFinanceiroService;

    private final RelatorioFinanceiroService relatorioFinanceiroService;

    @GetMapping
    public ResponseEntity<List<DetalheRelatorioFinanceiroDTO>> findAll() {
        try {
            Iterable<DetalheRelatorioFinanceiro> despesas = detalheRelatorioFinanceiroService.findAll();

            List<DetalheRelatorioFinanceiroDTO> despesaDTO = new ArrayList<>();

            despesas.forEach(despesa -> despesaDTO.add(convertToDTO(despesa)));

            return ResponseEntity.ok(despesaDTO);

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheRelatorioFinanceiroDTO> findById(@PathVariable Long id) {
        try {
            var detalhe = detalheRelatorioFinanceiroService.findById(id);

            return ResponseEntity.ok(new DetalheRelatorioFinanceiroDTO(detalhe));

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DetalheRelatorioFinanceiroDTO detalheDTO) {
        try {
            var newDetalhe = detalheRelatorioFinanceiroService.create(convertToEntity(detalheDTO));

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newDetalhe.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
            @RequestBody DetalheRelatorioFinanceiroDTO detalheDTO) {
        try {
            detalheRelatorioFinanceiroService.update(id, convertToEntity(detalheDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            detalheRelatorioFinanceiroService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private DetalheRelatorioFinanceiro convertToEntity(DetalheRelatorioFinanceiroDTO detalheDTO) {
        var detalhe = new DetalheRelatorioFinanceiro();

        detalhe.setId(detalheDTO.getId());

        detalhe.setDescricao(detalheDTO.getDescricao());

        detalhe.setValor(detalheDTO.getValor());

        detalhe.setRelatorio(fetchRelatorio(detalheDTO.getRelatorio()));

        return detalhe;

    }

    private RelatorioFinanceiro fetchRelatorio(Long id) {
        return relatorioFinanceiroService.findById(id);

    }

    private DetalheRelatorioFinanceiroDTO convertToDTO(DetalheRelatorioFinanceiro detalheRelatorioFinanceiro) {

        return new DetalheRelatorioFinanceiroDTO(detalheRelatorioFinanceiro);

    }

}
