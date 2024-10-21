package mz.co.mefemasys.xicola.backend.controllers;

import jakarta.persistence.EntityNotFoundException;

import lombok.Data;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import mz.co.mefemasys.xicola.backend.dto.DocumentoDTO;

import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;

import mz.co.mefemasys.xicola.backend.models.Documento;

import mz.co.mefemasys.xicola.backend.models.Estado;

import mz.co.mefemasys.xicola.backend.models.Funcionario;

import mz.co.mefemasys.xicola.backend.models.TipoDocumento;

import mz.co.mefemasys.xicola.backend.service.DocumentoService;

import mz.co.mefemasys.xicola.backend.service.EstadoService;

import mz.co.mefemasys.xicola.backend.service.FuncionarioService;

import mz.co.mefemasys.xicola.backend.service.TipoDocumentoService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.util.List;

import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/documentos")
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class DocumentoController {

    private static final Logger LOG = Logger.getLogger(DocumentoController.class.getName());

    private final DocumentoService documentoService;

    private final TipoDocumentoService tipoDocumentoService;

    private final FuncionarioService funcionarioService;

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> findAll() {
        try {
            var documentos = documentoService.findAll();

            var documentosDTO = documentos.stream()
                    .map(this::convertToDTO)
                    .collect(toList());

            return new ResponseEntity<>(documentosDTO, OK);

        } catch (Exception e) {
            log.error("Erro ao buscar todos os documentos", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> findDocumentoById(@PathVariable Long id) {
        try {
            var documento = documentoService.findById(id);

            return ResponseEntity.ok(new DocumentoDTO(documento));

        } catch (EntityNotFoundException e) {
            log.error("Documento não encontrado com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao buscar documento com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DocumentoDTO documentoDTO) {
        try {
            var newDocumento = documentoService.create(convertToEntity(documentoDTO));

            var newDocumentoDTO = convertToDTO(newDocumento);

            URI location = fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newDocumentoDTO.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (EntityNotFoundException e) {
            log.error("Tipo de documento, autor ou estado não encontrado", e);

            return new ResponseEntity<>(BAD_REQUEST);

        } catch (Exception e) {
            log.error("Erro ao criar novo documento", e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DocumentoDTO documentoDTO) {
        try {
            documentoService.update(id, convertToEntity(documentoDTO));

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Documento, Tipo de documento, autor ou estado não encontrado para o ID: " + id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao atualizar documento com o ID: " + id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            documentoService.delete(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            log.error("Documento não encontrado para remoção com o ID: {}", id, e);

            return new ResponseEntity<>(NOT_FOUND);

        } catch (InternalServerErrorException e) {
            log.error("Erro ao remover documento com o ID: {}", id, e);

            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);

        }
    }

    private Documento convertToEntity(DocumentoDTO documentoDTO) {
        var documento = new Documento();

        documento.setId(documentoDTO.getId());

        documento.setTitulo(documentoDTO.getTitulo());

        documento.setConteudo(documentoDTO.getConteudo());

        documento.setDataCriacao(documentoDTO.getDataCriacao());

        TipoDocumento tipoDocumento = tipoDocumentoService.findTipoDocumento(documentoDTO.getTipoDocumento());

        documento.setTipoDocumento(tipoDocumento);

        Funcionario autor = funcionarioService.findById(documentoDTO.getAutor());

        documento.setAutor(autor);

        Estado estado = estadoService.findEstado(documentoDTO.getEstado());

        documento.setEstado(estado);

        return documento;

    }

    private DocumentoDTO convertToDTO(Documento documento) {
        return new DocumentoDTO(documento);

    }
}
