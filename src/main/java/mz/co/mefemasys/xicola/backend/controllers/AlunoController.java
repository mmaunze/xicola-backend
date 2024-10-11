package mz.co.mefemasys.xicola.backend.controllers;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Distrito;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.dto.AlunoDTO;
import mz.co.mefemasys.xicola.backend.service.AlunoService;
import mz.co.mefemasys.xicola.backend.service.DistritoService;
import mz.co.mefemasys.xicola.backend.service.EstadoService;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;

import mz.co.mefemasys.xicola.backend.service.ProvinciaService;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.springframework.http.ResponseEntity.*;

import org.springframework.web.bind.annotation.*;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/alunos")
@Slf4j
public class AlunoController implements MetodosGerais {

    private final EstadoService estadoService;
    private final AlunoService alunoService;
    private final ProvinciaService provinciaService;
    private final DistritoService distritoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PEDAGOGICO') or hasRole('PROFESSOR')")
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
    @PreAuthorize("#id == principal.id or hasRole('ADMIN') or hasRole('PEDAGOGICO') or hasRole('PROFESSOR')")
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


    @GetMapping("/totais")
    public ResponseEntity<Long> totais() {
        var total = alunoService.count();
        return new ResponseEntity<>(total, OK);
    }

    @GetMapping("/alunos/{estado}")
    public ResponseEntity<Long> totalAlunosEstado(String estado) {
        var total = alunoService.totalAlunosEstado(estado);
        return new ResponseEntity<>(total, OK);
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Void> create(@RequestBody AlunoDTO aluno) {
        try {
            var newAluno = alunoService.create(aluno);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newAluno.getId())
                    .toUri();
            return created(location).build();
        } catch (EntityNotFoundException e) {
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

            aluno.setDataNascimento(converterStringParaData(alunoDTO.getDataNascimento()));


        aluno.setDistritoNascimento(fectchDistrito(alunoDTO.getDistritoNascimento()));
        aluno.setSexo(alunoDTO.getSexo());
        aluno.setBilheteIdentificacao(alunoDTO.getBilheteIdentificacao());
        aluno.setReligiao(alunoDTO.getReligiao());
        aluno.setGrupoSanguineo(alunoDTO.getGrupoSanguineo());
        aluno.setEndereco(alunoDTO.getEndereco());
        aluno.setDataRegisto(alunoDTO.getDataRegisto());
        Estado estado = estadoService.findById(Long.parseLong("1")); //findEstado(alunoDTO.getEstado());
        aluno.setEstado(estado);
        aluno.setEscolaAnterior(alunoDTO.getEscolaAnterior());
        aluno.setNomeDoPai(alunoDTO.getNomeDoPai());
        aluno.setNomeDaMae(alunoDTO.getNomeDaMae());
        aluno.setNumeroTelefonePrincipal(alunoDTO.getNumeroTelefonePrincipal());
        return aluno;
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        return new AlunoDTO(aluno);
    }

    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);
    }


}
