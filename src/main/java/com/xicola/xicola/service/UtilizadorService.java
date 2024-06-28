package com.xicola.xicola.service;

import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.Utilizador;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.UtilizadorRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UtilizadorService {

    private static final String UTILIZADOR_NOT_FOUND_MESSAGE = "Utilizador não encontrado com o ID: ";
    private static final String USERNAME_VAZIO_MESSAGE = "O username não pode estar vazio";
    private static final String USERNAME_CURTO_MESSAGE = "Username curto demais";
    private static final String SENHA_VAZIA_MESSAGE = "A senha não pode estar vazia";
    private static final String SENHA_CURTA_MESSAGE = "Senha curta demais";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";


    private final UtilizadorRepository utilizadorRepository;
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public Utilizador findById(Long id) {
        return utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UTILIZADOR_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Utilizador> findAll() {
        return utilizadorRepository.findAll();
    }

    @Transactional
    public Utilizador create(Utilizador utilizador) {
        var estadoOptional = estadoRepository.findEstado("Activo");
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        validarDadosObrigatorios(utilizador);
        validarComprimentoMinimo(utilizador);

        utilizador.setEstado(estado);

        return utilizadorRepository.save(utilizador);
    }

    @Transactional
    public Utilizador update(Long id, Utilizador utilizadorAtualizado) {
        var utilizadorOptional = utilizadorRepository.findById(id);
        if (utilizadorOptional.isEmpty()) {
            throw new ResourceNotFoundException(UTILIZADOR_NOT_FOUND_MESSAGE + id);
        }

        validarDadosObrigatorios(utilizadorAtualizado);
        validarComprimentoMinimo(utilizadorAtualizado);

        var utilizadorExistente = utilizadorOptional.get();

        utilizadorExistente.setUsername(utilizadorAtualizado.getUsername());
        utilizadorExistente.setSenha(utilizadorAtualizado.getSenha());
        utilizadorExistente.setEstado(utilizadorAtualizado.getEstado());
        utilizadorExistente.setTipoUtilizador(utilizadorAtualizado.getTipoUtilizador());

        return utilizadorRepository.save(utilizadorExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!utilizadorRepository.existsById(id)) {
            throw new ResourceNotFoundException(UTILIZADOR_NOT_FOUND_MESSAGE + id);
        }
        utilizadorRepository.deleteById(id);
    }


    private void validarDadosObrigatorios(Utilizador utilizador) {
        if (utilizador.getUsername() == null || utilizador.getUsername().isBlank()) {
            throw new BadRequestException(USERNAME_VAZIO_MESSAGE);
        }
        if (utilizador.getSenha() == null || utilizador.getSenha().isBlank()) {
            throw new BadRequestException(SENHA_VAZIA_MESSAGE);
        }
    }

    private void validarComprimentoMinimo(Utilizador utilizador) {
        if (utilizador.getUsername().length() < 4) {
            throw new BadRequestException("Username " + USERNAME_CURTO_MESSAGE);
        }
        if (utilizador.getSenha().length() < 6) {
            throw new BadRequestException("Senha " + SENHA_CURTA_MESSAGE);
        }
    }
}
