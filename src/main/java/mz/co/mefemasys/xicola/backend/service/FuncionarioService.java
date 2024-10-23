package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.dto.create.CreateFuncionarioDTO;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.FuncionarioRepository;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class FuncionarioService implements MetodosGerais {

    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionario não encontrado com o ID: ";

    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";

    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";

    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do Funcionario curto demais";

    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento do Funcionario não pode estar vazia";

    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private static final Logger LOG = Logger.getLogger(FuncionarioService.class.getName());

    private final FuncionarioRepository funcionarioRepository;

    private final EstadoRepository estadoRepository;

    private final UtilizadorRepository utilizadorRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final EstadoService estadoService;

    private final DistritoService distritoService;

    private final TransactionTemplate transactionTemplate;

    private final SectorTrabalhoService sectorTrabalhoService;

    private final CargoService cargoService;

    private final DepartamentoService departamentoService;
    private final AreaCientificaService areaCientificaService;

    @Transactional(readOnly = true)
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FUNCIONARIO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Long count() {
        return funcionarioRepository.count();

    }

    @Transactional(readOnly = true)
    public Long totalEstado(String estado) {

        if (estado == null || estado.trim().isEmpty()) {
            return 0L;

        }

        return funcionarioRepository.totalEstado(estado);

    }

    @Transactional
    public Funcionario create(CreateFuncionarioDTO funcionario) {

        log.info("Iniciando o processo de criação de funcionario...");

        log.info(funcionario.toString());

        long id = gerarId();

        log.info("ID gerado para o funcionario: {}", id);

        var username = gerarUsernameUnico(gerarUsernames(funcionario.getNomeCompleto()));

        log.info("Nome de utilizador gerado: {}", username);

        var email = funcionario.getEmail();

        var estado = fectchEstado("Activo");

        log.info("Estado do funcionario obtido: {}", estado.getDescricao());

        var distrito = fectchDistrito(funcionario.getDistritoNascimento());

        log.info("Distrito de nascimento obtido: {}", distrito.getNomeDistrito());


        /*
         * Cadastrar o utilizador primeiro
         */
        Utilizador utilizador = new Utilizador(username, funcionario.getNomeCompleto(), email,
                encoder.encode(username));

        utilizador.setId(id);

        log.info("Utilizador criado: {}", utilizador);

        Set<Role> roles = new HashSet<>();

        switch (funcionario.getCargo()) {
            case "Gestor(a) Financeiro(a)": {
                Role financeiroRole = roleRepository.findByName(ERole.ROLE_FINANCEIRO)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_FINANCEIRO' obtida.");

                roles.add(financeiroRole);

                break;

            }

            case "Coordenador(a) Pedagógico(a)": {
                Role pedagogicoRole = roleRepository.findByName(ERole.ROLE_PEDAGOGICO)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_PEDAGOGICO' obtida.");

                roles.add(pedagogicoRole);

                break;

            }

            case "Diretor(a)": {
                Role directoRole = roleRepository.findByName(ERole.ROLE_DIRECTOR)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_DIRECTOR' obtida.");

                roles.add(directoRole);

                break;

            }

            case "Bibliotecário(a)": {
                Role bibliotecarioRole = roleRepository.findByName(ERole.ROLE_BIBLIOTECARIO)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_BIBLIOTECARIO' obtida.");

                roles.add(bibliotecarioRole);

                break;

            }

            case "Secretário(a) Escolar": {
                Role secretariaRole = roleRepository.findByName(ERole.ROLE_SECRETARIA)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_SECRETARIA' obtida.");

                roles.add(secretariaRole);

                break;

            }

            case "Assistente Administrativo": {
                Role assistenteAdminRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_MODERATOR' obtida.");

                roles.add(assistenteAdminRole);

                break;

            }

            case "Gestor(a) de Compras": {
                Role aquisicoesRole = roleRepository.findByName(ERole.ROLE_AQUISICOES)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_AQUISICOES' obtida.");

                roles.add(aquisicoesRole);

                break;

            }

            default: {

                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

                log.info("Role 'ROLE_USER' obtida.");

                roles.add(userRole);

            }

        }

        utilizador.setRoles(roles);

        log.info("Roles atribuídas ao utilizador: {}", roles);

        log.info("Salvando o utilizador...");

        utilizadorRepository.save(utilizador);

        utilizadorRepository.flush();

        log.info("Utilizador salvo com sucesso.");


        /*
         * Cadastrar o funcionario
         */
        Utilizador cadastrado = utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FUNCIONARIO_NOT_FOUND_MESSAGE + id));

        log.info("Usuario Encontrado {}", cadastrado.getId());

        var newFuncionario = new Funcionario();

        log.info("Instância do Funcionario criada.");

        newFuncionario.setUtilizador(cadastrado);
        newFuncionario.setEmail(cadastrado.getEmail());
        newFuncionario.setNomeCompleto(funcionario.getNomeCompleto());
        newFuncionario.setDataNascimento(funcionario.getDataNascimento());
        newFuncionario.setEndereco(funcionario.getEndereco());
        newFuncionario.setReligiao(funcionario.getReligiao());
        newFuncionario.setNomeDaMae(funcionario.getNomeDaMae());
        newFuncionario.setNomeDoPai(funcionario.getNomeDoPai());
        newFuncionario.setSexo(funcionario.getSexo());
        newFuncionario.setDistritoNascimento(distrito);
        newFuncionario.setEstado(estado);
        newFuncionario.setDataContracto(Instant.now());
        if (funcionario.getNumeroTelefoneAlternativo() == null) {
            funcionario.setNumeroTelefoneAlternativo(funcionario.getNumeroTelefonePrincipal());

        }
        newFuncionario.setEstadoCivil(funcionario.getEstadoCivil());
        newFuncionario.setNumeroTelefonePrincipal(funcionario.getNumeroTelefonePrincipal());
        newFuncionario.setNumeroTelefoneAlternativo(funcionario.getNumeroTelefoneAlternativo());
        newFuncionario.setCargo(fectchCargo(funcionario.getCargo()));
        newFuncionario.setBilheteIdentificacao(funcionario.getBilheteIdentificacao());
        newFuncionario.setDepartamento(fectchDepartamento(funcionario.getDepartamento()));
        newFuncionario.setGrupoSanguineo(funcionario.getGrupoSanguineo());
        newFuncionario.setAreaFormacao(fectchAreaCientifica(funcionario.getAreaFormacao()));

        log.info("Funcionario criado: {} {}", newFuncionario.getDataContracto(), newFuncionario.getDataNascimento());

        log.info("Salvando o funcionario...");

        funcionarioRepository.save(newFuncionario);

        log.info("Funcionario salvo com sucesso: {}", newFuncionario);

        return newFuncionario;

    }

    @Transactional
    public Funcionario update(Long id, Funcionario funcionarioAtualizado) {

        var funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isEmpty()) {
            throw new ResourceNotFoundException(FUNCIONARIO_NOT_FOUND_MESSAGE + id);

        }

        validarDadosObrigatorios(funcionarioAtualizado);

        validarComprimentoMinimo(funcionarioAtualizado);

        validarReligiao(funcionarioAtualizado);

        validarSexo(funcionarioAtualizado);

        validarEndereco(funcionarioAtualizado);

        validarDataNascimento(funcionarioAtualizado);

        validarEmail(funcionarioAtualizado);

        validarDataContracto(funcionarioAtualizado);

        validarTelefone(funcionarioAtualizado);

        // Outras validações específicas de atualização, se necessário
        var funcionarioExistente = funcionarioOptional.get();

        // Atualize outras propriedades conforme necessário
        funcionarioExistente.setNomeCompleto(funcionarioAtualizado.getNomeCompleto());

        funcionarioExistente.setEstadoCivil(funcionarioAtualizado.getEstadoCivil());

        funcionarioExistente.setBilheteIdentificacao(funcionarioAtualizado.getBilheteIdentificacao());

        funcionarioExistente.setReligiao(funcionarioAtualizado.getReligiao());

        funcionarioExistente.setSexo(funcionarioAtualizado.getSexo());

        funcionarioExistente.setEndereco(funcionarioAtualizado.getEndereco());

        funcionarioExistente.setDataNascimento(funcionarioAtualizado.getDataNascimento());

        funcionarioExistente.setDataContracto(funcionarioAtualizado.getDataContracto());

        funcionarioExistente.setEstado(funcionarioAtualizado.getEstado());

        funcionarioExistente.setDistritoNascimento(funcionarioAtualizado.getDistritoNascimento());

        funcionarioExistente.setAreaFormacao(funcionarioAtualizado.getAreaFormacao());

        funcionarioExistente.setGrupoSanguineo(funcionarioAtualizado.getGrupoSanguineo());

        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());

        funcionarioExistente.setNumeroTelefonePrincipal(funcionarioAtualizado.getNumeroTelefonePrincipal());

        funcionarioExistente.setNumeroTelefoneAlternativo(funcionarioAtualizado.getNumeroTelefoneAlternativo());


        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());

        return funcionarioRepository.save(funcionarioExistente);

    }

    @Transactional
    public void delete(Long id) {
        // Verifica se o Funcionario existe antes de excluí-lo
        if (!funcionarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(FUNCIONARIO_NOT_FOUND_MESSAGE + id);

        }

        funcionarioRepository.deleteById(id);

    }

    private void validarDadosObrigatorios(Funcionario funcionario) {
        if (funcionario.getNomeCompleto() == null || funcionario.getNomeCompleto().isBlank()) {
            throw new BadRequestException("Nome do Funcionario " + NOME_VAZIO_MESSAGE);

        }
        if (funcionario.getDataNascimento() == null) {
            throw new BadRequestException("Data de nascimento " + DATA_NASCIMENTO_VAZIA_MESSAGE);

        }
        if (funcionario.getSexo() == null || funcionario.getSexo().isBlank()) {
            throw new BadRequestException("Sexo " + NOME_VAZIO_MESSAGE);

        }
        if (funcionario.getEndereco() == null || funcionario.getEndereco().isBlank()) {
            throw new BadRequestException("Endereço " + NOME_VAZIO_MESSAGE);

        }
        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new BadRequestException("Email " + NOME_VAZIO_MESSAGE);

        }
        if (funcionario.getNumeroTelefonePrincipal() == 0) {
            throw new BadRequestException("Número de telefone principal " + NOME_VAZIO_MESSAGE);

        }
        if (funcionario.getDataContracto() == null) {
            throw new BadRequestException("Data de contrato " + DATA_NASCIMENTO_VAZIA_MESSAGE);

        }
    }

    private void validarComprimentoMinimo(Funcionario funcionario) {
        if (funcionario.getNomeCompleto().length() < 6) {
            throw new BadRequestException("Nome do Funcionario " + NOME_CURTO_MESSAGE);

        }
        if (funcionario.getEndereco().length() < 10) {
            throw new BadRequestException("Endereço do Funcionario " + NOME_CURTO_MESSAGE);

        }
        if (funcionario.getBilheteIdentificacao() != null && funcionario.getBilheteIdentificacao().length() < 8) {
            throw new BadRequestException("Bilhete de identificação " + NOME_CURTO_MESSAGE);

        }
    }

    private void validarEmail(Funcionario funcionario) {
        if (!funcionario.getEmail().contains("@") || !funcionario.getEmail().contains(".")) {
            throw new BadRequestException("Email inválido");

        }
    }

    private void validarTelefone(Funcionario funcionario) {
        if (String.valueOf(funcionario.getNumeroTelefonePrincipal()).length() != 9) {
            throw new BadRequestException("Número de telefone principal inválido");

        }
        if (funcionario.getNumeroTelefoneAlternativo() != null
                && String.valueOf(funcionario.getNumeroTelefoneAlternativo()).length() != 9) {
            throw new BadRequestException("Número de telefone alternativo inválido");

        }
    }

    private void validarEndereco(Funcionario funcionario) {
        if (funcionario.getEndereco().length() < 20) {
            throw new BadRequestException("Endereco do aluno " + NOME_CURTO_MESSAGE);

        }
    }

    private void validarDataNascimento(Funcionario funcionario) {
        if (funcionario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);

        }
        if (calcularIdade(converterDataParaString(funcionario.getDataNascimento())) < 18) {
            throw new BadRequestException("O Funcionario deve ter pelo menos 18 anos de idade");

        }
    }

    private void validarDataContracto(Funcionario funcionario) {
        if (funcionario.getDataContracto().isAfter(Instant.from(LocalDate.now()))) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);

        }
    }

    private void validarReligiao(Funcionario funcionario) {
        if (funcionario.getReligiao().length() < 4) {
            throw new BadRequestException(RELIGIAO_CURTO_MESSAGE);

        }
    }

    private void validarSexo(Funcionario funcionario) {
        if (!funcionario.getSexo().equalsIgnoreCase("Masculino")
                && !funcionario.getSexo().equalsIgnoreCase("Feminino")) {
            throw new IllegalArgumentException("O sexo só pode ser \"Masculino\" ou \"Feminino\"");

        }
    }

    private String gerarUsernameUnico(List<String> usernames) {
        // Verifica a disponibilidade dos usernames e escolhe um único username único
        for (var username : usernames) {
            if (utilizadorRepository.findByUsername(username).isEmpty()) {
                return username;

            }
        }
        // Se nenhum username estiver disponível, gera um aleatório
        return usernames.get(0);

    }

    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);

    }

    private Estado fectchEstado(String estado) {
        return estadoService.findEstado(estado);

    }

    private Cargo fectchCargo(String cargo) {
        return cargoService.findCargo(cargo);

    }

    private Departamento fectchDepartamento(String departamento) {
        return departamentoService.findDepartamento(departamento);

    }

    private AreaCientifica fectchAreaCientifica(String areaCientifica) {
        return areaCientificaService.findArea(areaCientifica);
    }

}
