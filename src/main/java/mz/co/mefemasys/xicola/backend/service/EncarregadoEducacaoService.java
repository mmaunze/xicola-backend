package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.create.CreateEncarregadoDTO;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.repository.EncarregadoEducacaoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class EncarregadoEducacaoService implements MetodosGerais {

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

    private static final Logger LOG = Logger.getLogger(EncarregadoEducacaoService.class.getName());

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    private final UtilizadorRepository utilizadorRepository;

    private final EstadoRepository estadoRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final EstadoService estadoService;

    private final DistritoService distritoService;

    private final TransactionTemplate transactionTemplate;

    private final SectorTrabalhoService sectorTrabalhoService;

    @Transactional(readOnly = true)
    public EncarregadoEducacao findById(Long id) {
        return encarregadoEducacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENC_EDU_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<EncarregadoEducacao> findAll() {
        return encarregadoEducacaoRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Long count() {
        return encarregadoEducacaoRepository.count();

    }

    @Transactional(readOnly = true)
    public Long totalEstado(String estado) {

        if (estado == null || estado.trim().isEmpty()) {
            return 0L;

        }

        return encarregadoEducacaoRepository.totalEstado(estado);

    }

    @Transactional
    public EncarregadoEducacao create(CreateEncarregadoDTO encarregado) {

        log.info("Iniciando o processo de criação de encarregado...");
        log.info(encarregado.toString());
        long id = gerarId();
        log.info("ID gerado para o encarregado: {}", id);

        var username = gerarUsernameUnico(gerarUsernames(encarregado.getNomeCompleto()));
        log.info("Nome de utilizador gerado: {}", username);

        var email = encarregado.getEmail();

        var estado = fectchEstado("Activo");
        log.info("Estado do encarregado obtido: {}", estado.getDescricao());

        var distrito = fectchDistrito(encarregado.getDistritoNascimento());
        log.info("Distrito de nascimento obtido: {}", distrito.getNomeDistrito());


        /*
         * Cadastrar o utilizador primeiro
         */
        Utilizador utilizador = new Utilizador(username, encarregado.getNomeCompleto(), email,
                encoder.encode(username));

        utilizador.setId(id);

        log.info("Utilizador criado: {}", utilizador);

        Set<Role> roles = new HashSet<>();

        Role encarregadoRole = roleRepository.findByName(ERole.ROLE_ENCARREGADO)
                .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

        log.info("Role 'ROLE_ENCARREGADO' obtida.");

        roles.add(encarregadoRole);

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

        log.info("Role 'ROLE_USER' obtida.");

        roles.add(userRole);

        utilizador.setRoles(roles);

        log.info("Roles atribuídas ao utilizador: {}", roles);

        log.info("Salvando o utilizador...");

        utilizadorRepository.save(utilizador);

        utilizadorRepository.flush();

        log.info("Utilizador salvo com sucesso.");


        /*
         * Cadastrar o encarregado
         */
        Utilizador cadastrado = utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENC_EDU_NOT_FOUND_MESSAGE + id));

        log.info("Usuario Encontrado {}", cadastrado.getId());

        var newEncarregado = new EncarregadoEducacao();

        log.info("Instância do Encarregado criada.");

        newEncarregado.setUtilizador(cadastrado);
        newEncarregado.setEmail(cadastrado.getEmail());
        newEncarregado.setNomeCompleto(encarregado.getNomeCompleto());
        newEncarregado.setDataNascimento(encarregado.getDataNascimento());
        newEncarregado.setEndereco(encarregado.getEndereco());
        newEncarregado.setReligiao(encarregado.getReligiao());
        newEncarregado.setNomeDaMae(encarregado.getNomeDaMae());
        newEncarregado.setNomeDoPai(encarregado.getNomeDoPai());
        newEncarregado.setSexo(encarregado.getSexo());
        newEncarregado.setDistritoNascimento(distrito);
        newEncarregado.setEstado(estado);
        newEncarregado.setEstadoCivil(encarregado.getEstadoCivil());
        newEncarregado.setDataRegisto(Instant.now());
        if (encarregado.getNumeroTelefoneAlternativo() == null) {
            encarregado.setNumeroTelefoneAlternativo(encarregado.getNumeroTelefonePrincipal());
        }
        newEncarregado.setNumeroTelefonePrincipal(encarregado.getNumeroTelefonePrincipal());
        newEncarregado.setNumeroTelefoneAlternativo(encarregado.getNumeroTelefoneAlternativo());
        newEncarregado.setSectorTrabalho(fectchSectorTrabalho(encarregado.getSectorTrabalho()));
        newEncarregado.setBilheteIdentificacao(encarregado.getBilheteIdentificacao());
        newEncarregado.setLocalTrabalho(encarregado.getLocalTrabalho());
        newEncarregado.setGrupoSanguineo(encarregado.getGrupoSanguineo());

        log.info("Encarregado criado: {} {}", newEncarregado.getDataRegisto(), newEncarregado.getDataNascimento());
        log.info("Salvando o encarregado...");
        encarregadoEducacaoRepository.save(newEncarregado);
        log.info("Encarregado salvo com sucesso: {}", newEncarregado);
        return newEncarregado;

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
        if (encarregadoEducacao.getNumeroTelefoneAlternativo() != null
                && String.valueOf(encarregadoEducacao.getNumeroTelefoneAlternativo()).length() != 9) {
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

    private String gerarUsernameUnico(List<String> usernames) {
        for (var username : usernames) {
            if (utilizadorRepository.findByUsername(username).isEmpty()) {
                return username;

            }
        }
        return usernames.get(0) + new Random().nextInt(1000);

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

    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);

    }

    private Estado fectchEstado(String estado) {
        return estadoService.findEstado(estado);

    }

    private SectorTrabalho fectchSectorTrabalho(String sectorTrabalho) {
        return sectorTrabalhoService.findSector(sectorTrabalho);

    }
}
