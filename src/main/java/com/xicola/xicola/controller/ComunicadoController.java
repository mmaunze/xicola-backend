package com.xicola.xicola.controller;

import com.xicola.xicola.model.Comunicado;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Funcionario;
import com.xicola.xicola.model.TipoPessoa;
import com.xicola.xicola.model.dto.ComunicadoDTO;
import com.xicola.xicola.service.ComunicadoService;
import com.xicola.xicola.service.EstadoService;
import com.xicola.xicola.service.FuncionarioService;
import com.xicola.xicola.service.TipoPessoaService;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geral/comunicados")
@Slf4j
public class ComunicadoController {

    private final ComunicadoService comunicadoService;
    private final TipoPessoaService tipoPessoaService;
    private final FuncionarioService funcionarioService;
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<ComunicadoDTO>> findAll() {
        try {
            var comunicados = comunicadoService.findAll();
            var comunicadoList = new ArrayList<Comunicado>();
            comunicados.forEach(comunicadoList::add); 

            var comunicadoDTOs = comunicadoList.stream()
                    .map(ComunicadoDTO::new)
                    .collect(toList());
            return new ResponseEntity<>(comunicadoDTOs, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os comunicados", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunicadoDTO> findById(@PathVariable Integer id) {
        try {
            var comunicado = comunicadoService.findById(id);
            return ResponseEntity.ok(new ComunicadoDTO(comunicado));
        } catch (EntityNotFoundException e) {
            log.error("Comunicado não encontrado com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar comunicado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    
    public ResponseEntity<Void> create(@RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            var newComunicado = comunicadoService.create(convertToEntity(comunicadoDTO));
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newComunicado.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar novo comunicado", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            comunicadoService.update(id, convertToEntity(comunicadoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Comunicado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar comunicado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            comunicadoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Comunicado não encontrado para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover comunicado com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Comunicado convertToEntity(ComunicadoDTO comunicadoDTO) {
        var comunicado = new Comunicado();
        comunicado.setId(comunicadoDTO.getId());
        comunicado.setTitulo(comunicadoDTO.getTitulo());
        comunicado.setConteudo(comunicadoDTO.getConteudo());
        comunicado.setDataPublicacao(comunicadoDTO.getDataPublicacao());

        comunicado.setResponsavel(fetchResponsavel(comunicadoDTO.getResponsavel()));
        comunicado.setDestinatario(fetchDestinatario(comunicadoDTO.getDestinatario()));
        comunicado.setEstado(fetchEstado(comunicadoDTO.getEstado()));

        return comunicado;
    }

    private Funcionario fetchResponsavel(Long id) {
        return funcionarioService.findById(id);
    }

    private TipoPessoa fetchDestinatario(Integer id) {
        return tipoPessoaService.findById(id);
    }

    private Estado fetchEstado(String estado) {
        return estadoService.findEstado(estado)
                .orElseThrow(() -> new ResourceNotFoundException("Estado nao encontrado"));

    }
}
