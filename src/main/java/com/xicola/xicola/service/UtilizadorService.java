package com.xicola.xicola.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.conf.SecurityConfig;
import com.xicola.xicola.model.Provider;
import com.xicola.xicola.model.Utilizador;
import com.xicola.xicola.model.dto.LoginUserDto;
import com.xicola.xicola.model.dto.RecoveryJwtTokenDto;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.UtilizadorRepository;
import com.xicola.xicola.security.JwtTokenService;
import com.xicola.xicola.security.MyUtilizadorDetails;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    private final SecurityConfig securityConfiguration;

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
    utilizador.setSenha(securityConfiguration.passwordEncoder().encode(utilizador.getSenha())) ;
  // Atribui ao usuário uma permissão específica

  utilizador.setEnabled(true);
  utilizador.setProvider(Provider.LOCAL);
  
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

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        MyUtilizadorDetails userDetails = (MyUtilizadorDetails) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }


}
