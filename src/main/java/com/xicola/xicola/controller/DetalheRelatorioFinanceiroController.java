package com.xicola.xicola.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xicola.xicola.model.DetalheRelatorioFinanceiro;
import com.xicola.xicola.model.RelatorioFinanceiro;
import com.xicola.xicola.model.dto.DetalheRelatorioFinanceiroDTO;
import com.xicola.xicola.service.DetalheRelatorioFinanceiroService;
import com.xicola.xicola.service.RelatorioFinanceiroService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/financeiro/relatorios/detalhes")
public class DetalheRelatorioFinanceiroController {

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
    public ResponseEntity<DetalheRelatorioFinanceiroDTO> findById(@PathVariable Integer id) {
        try {
            var detalhe = detalheRelatorioFinanceiroService.findById(id);
            return ResponseEntity.ok(new DetalheRelatorioFinanceiroDTO(detalhe));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
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
    public ResponseEntity<Void> update(@PathVariable Integer id,
            @RequestBody DetalheRelatorioFinanceiroDTO detalheDTO) {
        try {
            detalheRelatorioFinanceiroService.update(id, convertToEntity(detalheDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            detalheRelatorioFinanceiroService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private DetalheRelatorioFinanceiro convertToEntity(DetalheRelatorioFinanceiroDTO detalheDTO) {
        var detalhe = new DetalheRelatorioFinanceiro();
        detalhe.setId(detalheDTO.getId());
        detalhe.setDescricao(detalheDTO.getDescricao());
        detalhe.setValor(detalheDTO.getValor());

        detalhe.setRelatorio(fetchRelatorio(detalheDTO.getRelatorioId()));

        return detalhe;
    }

    private RelatorioFinanceiro fetchRelatorio(Integer id) {
        return relatorioFinanceiroService.findById(id);
    }

    private DetalheRelatorioFinanceiroDTO convertToDTO(DetalheRelatorioFinanceiro detalheRelatorioFinanceiro) {

        return new DetalheRelatorioFinanceiroDTO(detalheRelatorioFinanceiro);
    }

}
