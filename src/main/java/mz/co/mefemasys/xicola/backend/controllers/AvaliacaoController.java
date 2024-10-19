package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.Avaliacao;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.dto.AvaliacaoDTO;
import mz.co.mefemasys.xicola.backend.service.AvaliacaoService;
import mz.co.mefemasys.xicola.backend.service.DisciplinaService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
import mz.co.mefemasys.xicola.backend.service.TipoAvaliacaoService;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

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
        } catch (InternalServerErrorException e) {
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
        } catch (InternalServerErrorException e) {
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
        } catch (InternalServerErrorException e) {
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
