package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import mz.co.mefemasys.xicola.backend.dto.create.CreateProfessorDTO;

import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;

import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;

import mz.co.mefemasys.xicola.backend.models.*;

import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;

import mz.co.mefemasys.xicola.backend.repository.ProfessorRepository;

import mz.co.mefemasys.xicola.backend.repository.RoleRepository;

import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;

import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;

import java.time.LocalDate;

import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Set;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class ProfessorService implements MetodosGerais {

    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "Professor não encontrado com o ID: ";

    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";

    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";

    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do professor curto demais";

    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento do professor não pode estar vazia";

    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private static final Logger LOG = Logger.getLogger(ProfessorService.class.getName());

    private final ProfessorRepository professorRepository;

    private final EstadoRepository estadoRepository;

    private final UtilizadorRepository utilizadorRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final EstadoService estadoService;

    private final DistritoService distritoService;

    private final TransactionTemplate transactionTemplate;

    private final AreaCientificaService areaCientificaService;

    @Transactional(readOnly = true)
    public Professor findById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROFESSOR_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Professor> findAll() {
        return professorRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Long count() {
        return professorRepository.count();

    }

    @Transactional(readOnly = true)
    public Long totalEstado(String estado) {

        if (estado == null || estado.trim().isEmpty()) {
            return 0L;

        }

        return professorRepository.totalEstado(estado);

    }

    @Transactional
    public Professor create(CreateProfessorDTO professor) {

        log.info("Iniciando o processo de criação de professor...");

        log.info(professor.toString());

        long id = gerarId();

        log.info("ID gerado para o professor: {}", id);

        var username = gerarUsernameUnico(gerarUsernames(professor.getNomeCompleto()));

        log.info("Nome de utilizador gerado: {}", username);

        var email = professor.getEmail();

        var estado = fectchEstado("Activo");

        log.info("Estado do professor obtido: {}", estado.getDescricao());

        var distrito = fectchDistrito(professor.getDistritoNascimento());

        log.info("Distrito de nascimento obtido: {}", distrito.getNomeDistrito());


        /*
         * Cadastrar o utilizador primeiro
         */
        Utilizador utilizador = new Utilizador(username, professor.getNomeCompleto(), email,
                encoder.encode(username));

        utilizador.setId(id);

        log.info("Utilizador criado: " + utilizador);

        Set<Role> roles = new HashSet<>();

        Role professorRole = roleRepository.findByName(ERole.ROLE_PROFESSOR)
                .orElseThrow(() -> new ResourceNotFoundException("a Role não foi  encontrada."));

        log.info("Role 'ROLE_PROFESSOR' obtida.");

        roles.add(professorRole);

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

        Utilizador cadastrado = utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROFESSOR_NOT_FOUND_MESSAGE + id));

        log.info("Usuario Encontrado {}", cadastrado.getId());


        /*
         * Cadastrar o professor
         */
        var newProfessor = new Professor();

        log.info("Instância do Professor criada.");

        newProfessor.setUtilizador(cadastrado);

        newProfessor.setEmail(cadastrado.getEmail());

        newProfessor.setDataContracto(Instant.now());

        newProfessor.setNomeCompleto(professor.getNomeCompleto());

        newProfessor.setDataNascimento(professor.getDataNascimento());

        newProfessor.setEndereco(professor.getEndereco());

        newProfessor.setReligiao(professor.getReligiao());

        newProfessor.setNomeDaMae(professor.getNomeDaMae());

        newProfessor.setNomeDoPai(professor.getNomeDoPai());

        newProfessor.setSexo(professor.getSexo());

        newProfessor.setDistritoNascimento(distrito);

        newProfessor.setEstado(estado);

        if (professor.getNumeroTelefoneAlternativo() == null) {
            professor.setNumeroTelefoneAlternativo(professor.getNumeroTelefonePrincipal());

        }

        newProfessor.setNumeroTelefonePrincipal(professor.getNumeroTelefonePrincipal());

        newProfessor.setNumeroTelefoneAlternativo(professor.getNumeroTelefoneAlternativo());

        newProfessor.setEstadoCivil(professor.getEstadoCivil());

        newProfessor.setAreaFormacao(fectchAreaCientifica(professor.getAreaFormacao()));

        newProfessor.setBilheteIdentificacao(professor.getBilheteIdentificacao());

        newProfessor.setEscolaAnterior(professor.getEscolaAnterior());

        newProfessor.setGrupoSanguineo(professor.getGrupoSanguineo());

        log.info("Professor criado: {} {}", newProfessor.getDataContracto(), newProfessor.getDataNascimento());

        log.info("Salvando o professor...");

        professorRepository.save(newProfessor);

        log.info("Professor salvo com sucesso: {}", newProfessor);

        return newProfessor;

    }

    @Transactional
    public Professor update(Long id, Professor professorAtualizado) {
        var professorExistente = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROFESSOR_NOT_FOUND_MESSAGE + id));

        validarProfessor(professorAtualizado);

        atualizarProfessor(professorExistente, professorAtualizado);

        return professorRepository.save(professorExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException(PROFESSOR_NOT_FOUND_MESSAGE + id);

        }
        professorRepository.deleteById(id);

    }

    private void validarProfessor(Professor professor) {
        validarDadosObrigatorios(professor);

        validarComprimentoMinimo(professor);

        validarReligiao(professor);

        validarSexo(professor);

        validarEndereco(professor);

        validarDataNascimento(professor);

        validarEmail(professor);

        validarDataContracto(professor);

        validarTelefone(professor);

    }

    private void atualizarProfessor(Professor professorExistente, Professor professorAtualizado) {
        professorExistente.setNomeCompleto(professorAtualizado.getNomeCompleto());

        professorExistente.setEstadoCivil(professorAtualizado.getEstadoCivil());

        professorExistente.setBilheteIdentificacao(professorAtualizado.getBilheteIdentificacao());

        professorExistente.setReligiao(professorAtualizado.getReligiao());

        professorExistente.setSexo(professorAtualizado.getSexo());

        professorExistente.setEndereco(professorAtualizado.getEndereco());

        professorExistente.setDataNascimento(professorAtualizado.getDataNascimento());

        professorExistente.setDataContracto(professorAtualizado.getDataContracto());

        professorExistente.setEstado(professorAtualizado.getEstado());

        professorExistente.setDistritoNascimento(professorAtualizado.getDistritoNascimento());

        professorExistente.setAreaFormacao(professorAtualizado.getAreaFormacao());

        professorExistente.setGrupoSanguineo(professorAtualizado.getGrupoSanguineo());

        professorExistente.setEmail(professorAtualizado.getEmail());

        professorExistente.setNumeroTelefonePrincipal(professorAtualizado.getNumeroTelefonePrincipal());

        professorExistente.setNumeroTelefoneAlternativo(professorAtualizado.getNumeroTelefoneAlternativo());

    }

    private void validarDadosObrigatorios(Professor professor) {
        if (professor.getNomeCompleto() == null || professor.getNomeCompleto().isBlank()) {
            throw new BadRequestException("Nome do professor " + NOME_VAZIO_MESSAGE);

        }
        if (professor.getDataNascimento() == null) {
            throw new BadRequestException("Data de nascimento " + DATA_NASCIMENTO_VAZIA_MESSAGE);

        }
        if (professor.getSexo() == null || professor.getSexo().isBlank()) {
            throw new BadRequestException("Sexo " + NOME_VAZIO_MESSAGE);

        }
        if (professor.getEndereco() == null || professor.getEndereco().isBlank()) {
            throw new BadRequestException("Endereço " + NOME_VAZIO_MESSAGE);

        }
        if (professor.getEmail() == null || professor.getEmail().isBlank()) {
            throw new BadRequestException("Email " + NOME_VAZIO_MESSAGE);

        }
        if (professor.getNumeroTelefonePrincipal() == 0) {
            throw new BadRequestException("Número de telefone principal " + NOME_VAZIO_MESSAGE);

        }
        if (professor.getDataContracto() == null) {
            throw new BadRequestException("Data de contrato " + DATA_NASCIMENTO_VAZIA_MESSAGE);

        }
    }

    private void validarComprimentoMinimo(Professor professor) {
        if (professor.getNomeCompleto().length() < 6) {
            throw new BadRequestException("Nome do professor " + NOME_CURTO_MESSAGE);

        }
        if (professor.getEndereco().length() < 10) {
            throw new BadRequestException("Endereço do professor " + NOME_CURTO_MESSAGE);

        }
        if (professor.getBilheteIdentificacao() != null && professor.getBilheteIdentificacao().length() < 8) {
            throw new BadRequestException("Bilhete de identificação " + NOME_CURTO_MESSAGE);

        }
    }

    private void validarEmail(Professor professor) {
        if (!professor.getEmail().contains("@") || !professor.getEmail().contains(".")) {
            throw new BadRequestException("Email inválido");

        }
    }

    private void validarTelefone(Professor professor) {
        if (String.valueOf(professor.getNumeroTelefonePrincipal()).length() != 9) {
            throw new BadRequestException("Número de telefone principal inválido");

        }
        if (professor.getNumeroTelefoneAlternativo() != null
                && String.valueOf(professor.getNumeroTelefoneAlternativo()).length() != 9) {
            throw new BadRequestException("Número de telefone alternativo inválido");

        }
    }

    private void validarEndereco(Professor professor) {
        if (professor.getEndereco().length() < 20) {
            throw new BadRequestException("Endereço do professor " + NOME_CURTO_MESSAGE);

        }
    }

    private void validarDataNascimento(Professor professor) {
        if (professor.getDataNascimento().isAfter(LocalDate.now())) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);

        }
        if (calcularIdade(converterDataParaString(professor.getDataNascimento())) < 18) {
            throw new BadRequestException("O professor deve ter pelo menos 18 anos de idade");

        }
    }

    private void validarDataContracto(Professor professor) {
        if (professor.getDataContracto().isAfter(new Date().toInstant())) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);

        }
    }

    private void validarReligiao(Professor professor) {
        if (professor.getReligiao().length() < 4) {
            throw new BadRequestException(RELIGIAO_CURTO_MESSAGE);

        }
    }

    private void validarSexo(Professor professor) {
        if (!professor.getSexo().equalsIgnoreCase("Masculino") && !professor.getSexo().equalsIgnoreCase("Feminino")) {
            throw new BadRequestException("O sexo só pode ser \"Masculino\" ou \"Feminino\"");

        }
    }

    private String gerarUsernameUnico(List<String> usernames) {
        for (var username : usernames) {
            if (utilizadorRepository.findByUsername(username).isEmpty()) {
                return username;

            }
        }
        return usernames.get(0);

    }

    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);

    }

    private Estado fectchEstado(String estado) {
        return estadoService.findEstado(estado);

    }

    private AreaCientifica fectchAreaCientifica(String areaCientifica) {
        return areaCientificaService.findArea(areaCientifica);

    }

}
