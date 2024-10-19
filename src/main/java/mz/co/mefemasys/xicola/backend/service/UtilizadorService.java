package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Long count(){
        return utilizadorRepository.count();
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
  //  utilizador.setSenha(securityConfiguration.passwordEncoder().encode(utilizador.getSenha())) ;
  // Atribui ao usuário uma permissão específica


  
        validarDadosObrigatorios(utilizador);
        validarComprimentoMinimo(utilizador);



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
        utilizadorExistente.setPassword(utilizadorAtualizado.getPassword());
      //  utilizadorExistente.setEstado(utilizadorAtualizado.getEstado());
   //     utilizadorExistente.setTipoUtilizador(utilizadorAtualizado.getTipoUtilizador());

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
        if (utilizador.getPassword() == null || utilizador.getPassword().isBlank()) {
            throw new BadRequestException(SENHA_VAZIA_MESSAGE);
        }
    }

    private void validarComprimentoMinimo(Utilizador utilizador) {
        if (utilizador.getUsername().length() < 4) {
            throw new BadRequestException("Username " + USERNAME_CURTO_MESSAGE);
        }
        if (utilizador.getPassword().length() < 6) {
            throw new BadRequestException("Senha " + SENHA_CURTA_MESSAGE);
        }
    }


}
