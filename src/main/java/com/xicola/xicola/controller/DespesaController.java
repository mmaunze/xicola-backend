package com.xicola.xicola.controller;

import com.xicola.xicola.model.Despesa;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Funcionario;
import com.xicola.xicola.model.dto.DespesaDTO;
import com.xicola.xicola.service.CategoriaFinanceiraService;
import com.xicola.xicola.service.DespesaService;
import com.xicola.xicola.service.EstadoService;
import com.xicola.xicola.service.FuncionarioService;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financeiro/despesas")
@Slf4j
public class DespesaController {

    private final DespesaService despesaService;
    private final CategoriaFinanceiraService categoriaFinanceiraService;
    private final FuncionarioService funcionarioService;
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> findAll() {
        try {
            Iterable<Despesa> despesas = despesaService.findAll();
            List<DespesaDTO> despesaDTO = new ArrayList<>();
            despesas.forEach(despesa -> despesaDTO.add(convertToDTO(despesa)));
            return ResponseEntity.ok(despesaDTO);
        } catch (Exception e) {
            log.error("Erro ao buscar todas as despesas", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable Integer id) {
        try {
            Despesa despesa = despesaService.findById(id);
            return ResponseEntity.ok(convertToDTO(despesa));
        } catch (ResourceNotFoundException e) {
            log.error("Despesa não encontrada com o ID: " + id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao buscar despesa com o ID: " + id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DespesaDTO despesaDTO) {
        try {
            Despesa newDespesa = despesaService.create(convertToEntity(despesaDTO));
            URI location = URI.create("/financeiro/despesas/" + newDespesa.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar nova despesa", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody DespesaDTO despesaDTO) {
        try {
            despesaService.update(id, convertToEntity(despesaDTO));
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            log.error("Despesa não encontrada para o ID: " + id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao atualizar despesa com o ID: " + id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            despesaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            log.error("Despesa não encontrada para remoção com o ID: " + id, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao remover despesa com o ID: " + id, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    private DespesaDTO convertToDTO(Despesa despesa) {
     
        return new DespesaDTO(despesa);
    }

    private Despesa convertToEntity(DespesaDTO despesaDTO) {
        Despesa despesa = new Despesa();
        despesa.setId(despesaDTO.getId());
        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setDataDespesa(despesaDTO.getDataDespesa());
        despesa.setCategoria(categoriaFinanceiraService.findById(despesaDTO.getCategoria()));
        despesa.setResponsavel(fetchResponsavel(despesaDTO.getResponsavel()));
        despesa.setEstado(fetchEstado(despesaDTO.getEstado()));

        return despesa;
    }

    private Estado fetchEstado(String estado) {
        return estadoService.findEstado(estado)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));
    }

    private Funcionario fetchResponsavel(Long id) {
        return funcionarioService.findById(id);
    }
}
