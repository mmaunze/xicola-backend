package com.xicola.xicola.service;

import com.xicola.xicola.model.Departamento;
import com.xicola.xicola.repository.DepartamentoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private static final String DEPARTAMENTO_NOT_FOUND_MESSAGE = "Departamento não encontrado com o ID: ";
    private static final String DEPARTAMENTO_NOT_FOUND_BY_SIGLA_MESSAGE = "Departamento não encontrado com a sigla: ";
    private static final String DESCRICAO_UNICA_MESSAGE = "Já existe um departamento com esta descrição: ";
    private static final String SIGLA_UNICA_MESSAGE = "Já existe um departamento com esta sigla: ";

    private final DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public Departamento findById(Integer id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTAMENTO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Departamento findDepartamento(String departamento) {
        return departamentoRepository.findByDescricao(departamento)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTAMENTO_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Departamento create(Departamento departamento) {
        validarDepartamentoUnico(null, departamento.getDescricao(), departamento.getSigla());
        return departamentoRepository.save(departamento);
    }

    @Transactional
    public Departamento update(Integer id, Departamento departamentoAtualizado) {
        validarDepartamentoExistente(id);
        validarDepartamentoUnico(id, departamentoAtualizado.getDescricao(), departamentoAtualizado.getSigla());

        var departamentoExistente = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTAMENTO_NOT_FOUND_MESSAGE + id));

        mapearDepartamentoAtualizado(departamentoExistente, departamentoAtualizado);

        return departamentoRepository.save(departamentoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        validarDepartamentoExistente(id);
        departamentoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Departamento> findByDescricao(String descricao) {
        return departamentoRepository.findByDescricao(descricao);
    }

    @Transactional(readOnly = true)
    public Departamento findBySigla(String sigla) {
        return departamentoRepository.findBySigla(sigla)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTAMENTO_NOT_FOUND_BY_SIGLA_MESSAGE + sigla));
    }

    private void validarDepartamentoExistente(Integer id) {
        if (!departamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(DEPARTAMENTO_NOT_FOUND_MESSAGE + id);
        }
    }

    private void validarDepartamentoUnico(Integer id, String descricao, String sigla) {
        var departamentoByDescricao = departamentoRepository.findByDescricao(descricao);
        departamentoByDescricao.ifPresent(dept -> {
            if (!dept.getId().equals(id)) {
                throw new BadRequestException(DESCRICAO_UNICA_MESSAGE + descricao);
            }
        });

        var departamentoBySigla = departamentoRepository.findBySigla(sigla);
        departamentoBySigla.ifPresent(dept -> {
            if (!dept.getId().equals(id)) {
                throw new BadRequestException(SIGLA_UNICA_MESSAGE + sigla);
            }
        });
    }

    private void mapearDepartamentoAtualizado(Departamento departamentoExistente, Departamento departamentoAtualizado) {
        departamentoExistente.setDescricao(departamentoAtualizado.getDescricao());
        departamentoExistente.setSigla(departamentoAtualizado.getSigla());
    }
}
