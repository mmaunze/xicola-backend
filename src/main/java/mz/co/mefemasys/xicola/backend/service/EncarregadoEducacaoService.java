package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.repository.EncarregadoEducacaoRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncarregadoEducacaoService {

    private static final String ENC_EDU_NOT_FOUND_MESSAGE = "Encarregado de Educação não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";
    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";
    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento não pode estar vazia";
    private static final String SEXO_VAZIO_MESSAGE = "O sexo não pode estar vazio";
    private static final String ENDERECO_VAZIO_MESSAGE = "O endereço não pode estar vazio";
    private static final String EMAIL_VAZIO_MESSAGE = "O email não pode estar vazio";
    private static final String TELEFONE_PRINCIPAL_VAZIO_MESSAGE = "O número de telefone principal não pode estar vazio";
    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do encarregado de educação curto demais";
    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;
    private final UtilizadorRepository utilizadorRepository;

    @Transactional(readOnly = true)
    public EncarregadoEducacao findById(Long id) {
        return encarregadoEducacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENC_EDU_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<EncarregadoEducacao> findAll() {
        return encarregadoEducacaoRepository.findAll();
    }

    @Transactional
    public EncarregadoEducacao create(EncarregadoEducacao encarregadoEducacao) {
        validarEncarregadoEducacao(encarregadoEducacao);

        encarregadoEducacao.setId(Long.parseLong(gerarId()));
        var utilizador = criarUtilizadorParaEncarregado(encarregadoEducacao);
        encarregadoEducacao.setUtilizador(utilizador);

        return encarregadoEducacaoRepository.save(encarregadoEducacao);
    }

    @Transactional
    public EncarregadoEducacao update(Long id, EncarregadoEducacao encarregadoAtualizado) {
        var encarregadoExistente = encarregadoEducacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENC_EDU_NOT_FOUND_MESSAGE + id));

        validarEncarregadoEducacao(encarregadoAtualizado);

        atualizarEncarregado(encarregadoExistente, encarregadoAtualizado);

        return encarregadoEducacaoRepository.save(encarregadoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!encarregadoEducacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENC_EDU_NOT_FOUND_MESSAGE + id);
        }
        encarregadoEducacaoRepository.deleteById(id);
    }

    private void validarEncarregadoEducacao(EncarregadoEducacao encarregadoEducacao) {
        validarDadosObrigatorios(encarregadoEducacao);
        validarComprimentoMinimo(encarregadoEducacao);
        validarReligiao(encarregadoEducacao);
        validarDataNascimento(encarregadoEducacao);
        validarEmail(encarregadoEducacao);
        validarTelefone(encarregadoEducacao);
    }

    private void validarDadosObrigatorios(EncarregadoEducacao encarregadoEducacao) {
        if (encarregadoEducacao.getNomeCompleto() == null || encarregadoEducacao.getNomeCompleto().isBlank()) {
            throw new BadRequestException("Nome do encarregado de educação " + NOME_VAZIO_MESSAGE);
        }
        if (encarregadoEducacao.getDataNascimento() == null) {
            throw new BadRequestException("Data de nascimento " + DATA_NASCIMENTO_VAZIA_MESSAGE);
        }
        if (encarregadoEducacao.getSexo() == null || encarregadoEducacao.getSexo().isBlank()) {
            throw new BadRequestException("Sexo " + SEXO_VAZIO_MESSAGE);
        }
        if (encarregadoEducacao.getEndereco() == null || encarregadoEducacao.getEndereco().isBlank()) {
            throw new BadRequestException("Endereço " + ENDERECO_VAZIO_MESSAGE);
        }
        if (encarregadoEducacao.getEmail() == null || encarregadoEducacao.getEmail().isBlank()) {
            throw new BadRequestException("Email " + EMAIL_VAZIO_MESSAGE);
        }
        if (encarregadoEducacao.getNumeroTelefonePrincipal() == 0) {
            throw new BadRequestException("Número de telefone principal " + TELEFONE_PRINCIPAL_VAZIO_MESSAGE);
        }
    }

    private void validarComprimentoMinimo(EncarregadoEducacao encarregadoEducacao) {
        if (encarregadoEducacao.getNomeCompleto().length() < 6) {
            throw new BadRequestException("Nome do encarregado de educação " + NOME_CURTO_MESSAGE);
        }
        if (encarregadoEducacao.getEndereco().length() < 10) {
            throw new BadRequestException("Endereço do encarregado de educação " + NOME_CURTO_MESSAGE);
        }
    }

    private void validarEmail(EncarregadoEducacao encarregadoEducacao) {
        if (!encarregadoEducacao.getEmail().contains("@") || !encarregadoEducacao.getEmail().contains(".")) {
            throw new BadRequestException("Email inválido");
        }
    }

    private void validarTelefone(EncarregadoEducacao encarregadoEducacao) {
        if (String.valueOf(encarregadoEducacao.getNumeroTelefonePrincipal()).length() != 9) {
            throw new BadRequestException("Número de telefone principal inválido");
        }
        if (encarregadoEducacao.getNumeroTelefoneAlternativo() != null &&
                String.valueOf(encarregadoEducacao.getNumeroTelefoneAlternativo()).length() != 9) {
            throw new BadRequestException("Número de telefone alternativo inválido");
        }
    }

    private void validarDataNascimento(EncarregadoEducacao encarregadoEducacao) {
        if (encarregadoEducacao.getDataNascimento().isAfter(LocalDate.now())) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);
        }
    }

    private void validarReligiao(EncarregadoEducacao encarregadoEducacao) {
        if (encarregadoEducacao.getReligiao() != null && encarregadoEducacao.getReligiao().length() < 4) {
            throw new BadRequestException(RELIGIAO_CURTO_MESSAGE);
        }
    }

    private Utilizador criarUtilizadorParaEncarregado(EncarregadoEducacao encarregadoEducacao) {
        var utilizador = new Utilizador();
        utilizador.setId(encarregadoEducacao.getId()); // Utilizando o mesmo ID do encarregado

        List<String> usernames = gerarUsernames(encarregadoEducacao.getNomeCompleto());
        utilizador.setUsername(gerarUsernameUnico(usernames));
        utilizador.setPassword(encarregadoEducacao.getEmail()); // Utilizando o email como senha
       // utilizador.setPassword(encarregadoEducacao.getEstado()); // Supondo que o estado do utilizador está definido no
                                                               // objeto encarregadoEducacao

        utilizadorRepository.save(utilizador);
        return utilizador;
    }

    private String gerarUsernameUnico(List<String> usernames) {
        for (var username : usernames) {
            if (!utilizadorRepository.findByUsername(username).isPresent()) {
                return username;
            }
        }
        return usernames.get(0) + new Random().nextInt(1000);
    }

    private List<String> gerarUsernames(String nomeCompleto) {
        // Implemente lógica para gerar uma lista de usernames a partir do nome completo
        // Exemplo simples:
        return List.of(nomeCompleto.toLowerCase().replace(" ", "_"));
    }

    private String gerarId() {
        // Implemente lógica para gerar um ID único (exemplo simplificado usando
        // timestamp)
        return String.valueOf(System.currentTimeMillis());
    }

    private void atualizarEncarregado(EncarregadoEducacao encarregadoExistente,
            EncarregadoEducacao encarregadoAtualizado) {
        encarregadoExistente.setNomeCompleto(encarregadoAtualizado.getNomeCompleto());
        encarregadoExistente.setDataNascimento(encarregadoAtualizado.getDataNascimento());
        encarregadoExistente.setSexo(encarregadoAtualizado.getSexo());
        encarregadoExistente.setLocalTrabalho(encarregadoAtualizado.getLocalTrabalho());
        encarregadoExistente.setSectorTrabalho(encarregadoAtualizado.getSectorTrabalho());
        encarregadoExistente.setEndereco(encarregadoAtualizado.getEndereco());
        encarregadoExistente.setEmail(encarregadoAtualizado.getEmail());
        encarregadoExistente.setNumeroTelefonePrincipal(encarregadoAtualizado.getNumeroTelefonePrincipal());
        encarregadoExistente.setNumeroTelefoneAlternativo(encarregadoAtualizado.getNumeroTelefoneAlternativo());
        encarregadoExistente.setDistritoNascimento(encarregadoAtualizado.getDistritoNascimento());
    }
}
