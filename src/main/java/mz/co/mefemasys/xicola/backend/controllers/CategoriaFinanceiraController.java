package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.CategoriaFinanceiraDTO;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.models.CategoriaFinanceira;
import mz.co.mefemasys.xicola.backend.service.CategoriaFinanceiraService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financeiro/categorias-financeiras")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class CategoriaFinanceiraController {

    private static final Logger LOG = Logger.getLogger(CategoriaFinanceiraController.class.getName());

    private final CategoriaFinanceiraService categoriaFinanceiraService;

    @GetMapping
    public ResponseEntity<List<CategoriaFinanceiraDTO>> findAll() {
        try {
            var categorias = categoriaFinanceiraService.findAll();

            var categoriasList = new ArrayList<CategoriaFinanceira>();

            categorias.forEach(categoriasList::add);
            // Convert Iterable to List

            var categoriasDTO = categoriasList.stream()
                    .map(CategoriaFinanceiraDTO::new)
                    .collect(toList());

            return new ResponseEntity<>(categoriasDTO, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todas as categorias financeiras", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaFinanceiraDTO> findById(@PathVariable Long id) {
        try {
            var categoria = categoriaFinanceiraService.findById(id);

            return ResponseEntity.ok(new CategoriaFinanceiraDTO(categoria));

        } catch (EntityNotFoundException e) {
            log.error("Categoria financeira não encontrada com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar categoria financeira com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CategoriaFinanceiraDTO categoriaFinanceiraDTO) {
        try {
            var newCategoria = categoriaFinanceiraService.create(convertToEntity(categoriaFinanceiraDTO));

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newCategoria.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Erro ao criar nova categoria financeira", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody CategoriaFinanceiraDTO categoriaFinanceiraDTO) {
        try {
            categoriaFinanceiraService.update(id, convertToEntity(categoriaFinanceiraDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Categoria financeira não encontrada para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar categoria financeira com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            categoriaFinanceiraService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Categoria financeira não encontrada para remoção com o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover categoria financeira com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private CategoriaFinanceira convertToEntity(CategoriaFinanceiraDTO categoriaFinanceiraDTO) {
        var categoria = new CategoriaFinanceira();

        categoria.setId(categoriaFinanceiraDTO.getId());

        categoria.setDescricao(categoriaFinanceiraDTO.getNome());

        return categoria;

    }
}
