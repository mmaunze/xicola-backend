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

import com.xicola.xicola.model.Avaliacao;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.dto.AvaliacaoDTO;
import com.xicola.xicola.service.AvaliacaoService;
import com.xicola.xicola.service.DisciplinaService;
import com.xicola.xicola.service.EstadoService;
import com.xicola.xicola.service.TipoAvaliacaoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/academico/avaliacoes")
@RequiredArgsConstructor
@Slf4j
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final DisciplinaService disciplinaService;
    private final TipoAvaliacaoService tipoAvaliacaoService;
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> findAll() {
        try {
            List<Avaliacao> avaliacoes = avaliacaoService.findAll();
            List<AvaliacaoDTO> avaliacoesDTO = avaliacoes.stream().map(this::convertToDTO).collect(toList());
            return new ResponseEntity<>(avaliacoesDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todas as avaliações", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> findById(@PathVariable Long id) {
        try {
            Avaliacao avaliacao = avaliacaoService.findById(id);
            return ResponseEntity.ok(convertToDTO(avaliacao));
        } catch (EntityNotFoundException e) {
            log.error("Avaliação não encontrada com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar avaliação com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            Avaliacao newAvaliacao = convertToEntity(avaliacaoDTO);
            Avaliacao createdAvaliacao = avaliacaoService.create(newAvaliacao);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(createdAvaliacao.getId()).toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar nova avaliação", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            Avaliacao updatedAvaliacao = convertToEntity(avaliacaoDTO);
            avaliacaoService.update(id, updatedAvaliacao);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Avaliação não encontrada para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar avaliação com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            avaliacaoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Avaliação não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover avaliação com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Avaliacao convertToEntity(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoDTO.getId());
        avaliacao.setDisciplina(disciplinaService.findDisciplina(avaliacaoDTO.getDisciplina()));
        avaliacao.setTipoAvaliacao(tipoAvaliacaoService.findTipoAvaliacao(avaliacaoDTO.getTipo()));
        avaliacao.setTrimestre(avaliacaoDTO.getTrimestre());
        avaliacao.setObservacao(avaliacaoDTO.getObservacao());
        Estado estado = estadoService.findEstado(avaliacaoDTO.getEstado());
        avaliacao.setEstado(estado);

        return avaliacao;
    }

    private AvaliacaoDTO convertToDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(avaliacao);
    }
}
