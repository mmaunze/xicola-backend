package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.EncarregadoEducacaoDTO;
import mz.co.mefemasys.xicola.backend.dto.create.CreateEncarregadoDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;
import mz.co.mefemasys.xicola.backend.service.*;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
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
@RequestMapping("/encarregados-educacao")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class EncarregadoEducacaoController implements MetodosGerais {

    private static final Logger LOG = Logger.getLogger(EncarregadoEducacaoController.class.getName());
    private final EncarregadoEducacaoService encarregadoEducacaoService;
    private final UtilizadorService utilizadorService;
    private final SectorTrabalhoService sectorTrabalhoService;
    private final DistritoService distritoService;
    private final EstadoService estadoService;

    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO') or hasRole('ADMIN')")
    public ResponseEntity<List<EncarregadoEducacaoDTO>> findAll() {
        try {
            var encarregados = encarregadoEducacaoService.findAll();
            var encarregadosDTO = encarregados.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(encarregadosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os encarregados de educação", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/encarregado/{id}")
    @PreAuthorize("#id == principal.id or hasRole('PROFESSOR') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO') or hasRole('ADMIN')")
    public ResponseEntity<EncarregadoEducacaoDTO> findEncarregadoById(@PathVariable Long id) {
        try {
            var encarregado = encarregadoEducacaoService.findById(id);
            return ResponseEntity.ok(new EncarregadoEducacaoDTO(encarregado));
        } catch (EntityNotFoundException e) {
            log.error("Encarregado de educação não encontrado com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar encarregado de educação com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = encarregadoEducacaoService.count();
        return new ResponseEntity<>(total, OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Long> totalEncarregadoEstado(@PathVariable String estado) {
        var total = encarregadoEducacaoService.totalEncarregadosEstado(estado);
        return new ResponseEntity<>(total, OK);
    }

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody CreateEncarregadoDTO encarregado) {
        try {
            var encarregadoEducacao = encarregadoEducacaoService.create(encarregado);
            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(encarregadoEducacao.getId())
                    .toUri();
            return created(location).build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao criar novo encarregado", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO')")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        try {
            encarregadoEducacaoService.update(id, convertToEntity(encarregadoEducacaoDTO));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Encarregado de educação ou estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar encarregado de educação com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            encarregadoEducacaoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Encarregado de educação não encontrado para remoção com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover encarregado de educação com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private EncarregadoEducacao convertToEntity(EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        var encarregado = new EncarregadoEducacao();
        encarregado.setId(encarregadoEducacaoDTO.getId());
        encarregado.setNomeCompleto(encarregadoEducacaoDTO.getNomeCompleto());
        encarregado.setDataNascimento(converterStringParaData(encarregadoEducacaoDTO.getDataNascimento()));
        encarregado.setSexo(encarregadoEducacaoDTO.getSexo());
        encarregado.setReligiao(encarregadoEducacaoDTO.getReligiao());
        encarregado.setLocalTrabalho(encarregadoEducacaoDTO.getLocalTrabalho());
        encarregado.setSectorTrabalho(fetchSector(encarregadoEducacaoDTO.getSectorTrabalho()));
        encarregado.setEndereco(encarregadoEducacaoDTO.getEndereco());
        encarregado.setEmail(encarregadoEducacaoDTO.getEmail());
        encarregado.setGrupoSanguineo(encarregadoEducacaoDTO.getGrupoSanguineo());
        encarregado.setNumeroTelefonePrincipal(encarregadoEducacaoDTO.getNumeroTelefonePrincipal());
        encarregado.setNumeroTelefoneAlternativo(encarregadoEducacaoDTO.getNumeroTelefoneAlternativo());

        var utilizador = utilizadorService.findById(encarregadoEducacaoDTO.getId());
        encarregado.setUtilizador(utilizador);

        if (encarregadoEducacaoDTO.getDistritoNascimento() != null) {
            var distrito = distritoService.findDistrito(encarregadoEducacaoDTO.getDistritoNascimento());
            encarregado.setDistritoNascimento(distrito);
        }

        var estado = estadoService.findEstado(encarregadoEducacaoDTO.getEstado());
        encarregado.setEstado(estado);

        return encarregado;
    }

    private EncarregadoEducacaoDTO convertToDTO(EncarregadoEducacao encarregado) {
        return new EncarregadoEducacaoDTO(encarregado);
    }

    private SectorTrabalho fetchSector(String sector) {
        return sectorTrabalhoService.findSector(sector);
    }

}
