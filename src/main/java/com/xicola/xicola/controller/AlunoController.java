package com.xicola.xicola.controller;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.dto.AlunoDTO;
import com.xicola.xicola.service.AlunoService;
import com.xicola.xicola.service.EstadoService;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.springframework.http.ResponseEntity.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/utilizadores/alunos")
@Slf4j
public class AlunoController {

    private final EstadoService estadoService;
    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll() {
        try {
            var alunos = alunoService.findAll();
            var alunosDTO = alunos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());
            return new ResponseEntity<>(alunosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os alunos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/aluno/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMINISTRATOR')")
    public ResponseEntity<AlunoDTO> findAlunoById(@PathVariable Long id) {
        try {
            var aluno = alunoService.findById(id);
            return ResponseEntity.ok(new AlunoDTO(aluno));
        } catch (EntityNotFoundException e) {
            log.error("Aluno não encontrado com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar aluno com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AlunoDTO alunoDTO) {
        try {
            var newAluno = alunoService.create(convertToEntity(alunoDTO));
            var newAlunoDTO = convertToDTO(newAluno);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAlunoDTO.getId())
                    .toUri();

            return created(location).build();
        } catch (EntityNotFoundException e) {
            log.error("Estado não encontrado: " + alunoDTO.getEstado(), e);
            return new ResponseEntity<>(BAD_REQUEST);
        } catch (Exception e) {
            log.error("Erro ao criar novo aluno", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        try {
            alunoService.update(id, convertToEntity(alunoDTO));
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Aluno ou Estado não encontrado para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar aluno com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR );
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            alunoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Aluno não encontrado para remoção com o ID: {}", id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover aluno com o ID: {}", id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Aluno convertToEntity(AlunoDTO alunoDTO) {
        var aluno = new Aluno();
        aluno.setId(alunoDTO.getId());
        aluno.setNomeCompleto(alunoDTO.getNomeCompleto());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());
        aluno.setDistritoNascimento(alunoDTO.getDistritoNascimento());
        aluno.setSexo(alunoDTO.getSexo());
        aluno.setBilheteIdentificacao(alunoDTO.getBilheteIdentificacao());
        aluno.setReligiao(alunoDTO.getReligiao());
        aluno.setGrupoSanguineo(alunoDTO.getGrupoSanguineo());
        aluno.setEndereco(alunoDTO.getEndereco());
        aluno.setDataRegisto(alunoDTO.getDataRegisto());

        Estado estado = estadoService.findEstado(alunoDTO.getEstado())
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado: " + alunoDTO.getEstado()));
        aluno.setEstado(estado);

        aluno.setEscolaAnterior(alunoDTO.getEscolaAnterior());
        aluno.setNomeDoPai(alunoDTO.getNomeDoPai());
        aluno.setNomeDaMae(alunoDTO.getNomeDaMae());
        aluno.setNumeroTelefonePrincipal(alunoDTO.getNumeroTelefonePrincipal());
        return aluno;
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        var alunoDTO = new AlunoDTO(aluno);
        alunoDTO.setId(aluno.getId());
        alunoDTO.setNomeCompleto(aluno.getNomeCompleto());
        alunoDTO.setDataNascimento(aluno.getDataNascimento());
        alunoDTO.setDistritoNascimento(aluno.getDistritoNascimento());
        alunoDTO.setSexo(aluno.getSexo());
        alunoDTO.setBilheteIdentificacao(aluno.getBilheteIdentificacao());
        alunoDTO.setReligiao(aluno.getReligiao());
        alunoDTO.setGrupoSanguineo(aluno.getGrupoSanguineo());
        alunoDTO.setEndereco(aluno.getEndereco());
        alunoDTO.setDataRegisto(aluno.getDataRegisto());
        alunoDTO.setEstado(aluno.getEstado().getDescricao());
        alunoDTO.setEscolaAnterior(aluno.getEscolaAnterior());
        alunoDTO.setNomeDoPai(aluno.getNomeDoPai());
        alunoDTO.setNomeDaMae(aluno.getNomeDaMae());
        alunoDTO.setNumeroTelefonePrincipal(aluno.getNumeroTelefonePrincipal());
        return alunoDTO;
    }
}
