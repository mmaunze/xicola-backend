package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;
import mz.co.mefemasys.xicola.backend.models.dto.EncarregadoEducacaoDTO;
import mz.co.mefemasys.xicola.backend.service.*;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/utilizadores/encarregados")
@Slf4j
public class EncarregadoEducacaoController {

    private final EncarregadoEducacaoService encarregadoEducacaoService;
    private final UtilizadorService utilizadorService;
    private final SectorTrabalhoService sectorTrabalhoService;
    private final DistritoService distritoService;
    private final EstadoService estadoService;

    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO') or hasRole('ENCARREGADO')")
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

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('PROFESSOR') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO') or hasRole('ENCARREGADO')")
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

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        try {
            var newEncarregado = encarregadoEducacaoService.create(convertToEntity(encarregadoEducacaoDTO));
            var newEncarregadoDTO = convertToDTO(newEncarregado);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newEncarregadoDTO.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (EntityNotFoundException e) {
            log.error("Erro ao criar novo encarregado de educação", e);
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (Exception e) {
            log.error("Erro ao criar novo encarregado de educação", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENCARREGADO') or hasRole('DIRECTOR') or hasRole('PEDAGOGICO')")
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
    @PreAuthorize("hasRole('DIRECTOR') or hasRole('PEDAGOGICO')")
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
        encarregado.setDataNascimento(encarregadoEducacaoDTO.getDataNascimento());
        encarregado.setSexo(encarregadoEducacaoDTO.getSexo());
        encarregado.setReligiao(encarregadoEducacaoDTO.getReligiao());
        encarregado.setLocalTrabalho(encarregadoEducacaoDTO.getLocalTrabalho());
        encarregado.setSectorTrabalho(fetchSector(encarregadoEducacaoDTO.getSectorTrabalho()));
        encarregado.setEndereco(encarregadoEducacaoDTO.getEndereco());
        encarregado.setEmail(encarregadoEducacaoDTO.getEmail());
        encarregado.setGrupoSanguineo(encarregadoEducacaoDTO.getGrupoSanguineo());
        encarregado.setNumeroTelefonePrincipal(encarregadoEducacaoDTO.getNumeroTelefonePrincipal());
        encarregado.setNumeroTelefoneAlternativo(encarregadoEducacaoDTO.getNumeroTelefoneAlternativo());

        var utilizador = utilizadorService.findById(encarregadoEducacaoDTO.getUtilizador());
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
