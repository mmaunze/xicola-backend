package com.xicola.xicola.controller;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;
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

import com.xicola.xicola.model.Ativo;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.dto.AtivoDTO;
import com.xicola.xicola.service.AtivoService;
import com.xicola.xicola.service.EstadoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/aquisicao/ativos")
@Slf4j
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
    public ResponseEntity<AtivoDTO> findById(@PathVariable Integer id) {
        try {
            Ativo ativo = ativoService.findById(id);
            return ResponseEntity.ok(new AtivoDTO(ativo));
        } catch (EntityNotFoundException e) {
            log.error("Ativo não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
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
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody AtivoDTO ativoDTO) {
        try {
            ativoService.update(id, convertToEntity(ativoDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Ativo ou Estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar ativo com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            ativoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Ativo não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
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

        Estado estado = estadoService.findEstado(ativoDTO.getEstado())
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado: " + ativoDTO.getEstado()));
        ativo.setEstado(estado);

        return ativo;
    }

    private AtivoDTO convertToDTO(Ativo ativo) {
        return new AtivoDTO(ativo);
    }
}
