package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.InternalServerErrorException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.models.dto.create.CreateAlunoDTO;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class AlunoService implements MetodosGerais {

    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";
    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";
    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do aluno curto demais";
    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento do aluno não pode estar vazia";
    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final AlunoRepository alunoRepository;
    private final EstadoRepository estadoRepository;
    private final RoleRepository roleRepository;
    private final EstadoService estadoService;
    private final UtilizadorRepository utilizadorRepository;
    private final DistritoService distritoService;
    private  final PasswordEncoder encoder;
    private final TransactionTemplate transactionTemplate;

    @Transactional(readOnly = true)
    public Aluno findById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Long count(){
        return alunoRepository.count();
    }

    @Transactional(readOnly = true)
    public Long totalAlunosEstado(String estado) {

        if (estado == null || estado.trim().isEmpty()) {
            return 0L;
        }

        return alunoRepository.findAlunosByEstado(estado);
    }

    private static final Logger logger = LoggerFactory.getLogger(AlunoService.class);

    @Transactional
    public Aluno create(CreateAlunoDTO aluno) {

        logger.info("Iniciando o processo de criação de aluno...");
        log.info(aluno.toString());

        long id = gerarId();
        logger.info("ID gerado para o aluno: " + id);

        var username = gerarUsernameUnico(gerarUsernames(aluno.getNomeCompleto()));
        logger.info("Nome de utilizador gerado: " + username);

        var email = username + "@xicola.co.mz";
        logger.info("Email gerado para o utilizador: " + email);

        var estado = fectchEstado("Matriculado");
        logger.info("Estado do aluno obtido: " + estado.getDescricao());

        var distrito = fectchDistrito(aluno.getDistritoNascimento());
        logger.info("Distrito de nascimento obtido: " + distrito.getNomeDistrito());

        /*
         * Cadastrar o utilizador primeiro
         */

        Utilizador utilizador = new Utilizador(username, aluno.getNomeCompleto(), email,
                encoder.encode(username));
        utilizador.setId(id);
        logger.info("Utilizador criado: " + utilizador);

        Set<Role> roles = new HashSet<>();

        Role estudanteRole = roleRepository.findByName(ERole.ROLE_ESTUDANTE)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
        logger.info("Role 'ROLE_ESTUDANTE' obtida.");

        roles.add(estudanteRole);

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
        logger.info("Role 'ROLE_USER' obtida.");

        roles.add(userRole);
        utilizador.setRoles(roles);

        logger.info("Roles atribuídas ao utilizador: " + roles);

        logger.info("Salvando o utilizador...");
        utilizadorRepository.save(utilizador);
        utilizadorRepository.flush();
        logger.info("Utilizador salvo com sucesso.");

        /*
         * Cadastrar o aluno
         */

       Utilizador cadastrado = utilizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + id));

       log.info("Usuario Encontrado {}",cadastrado.getId());

        var newAluno = new Aluno();
        logger.info("Instância do Aluno criada.");

        newAluno.setUtilizador(cadastrado);
        newAluno.setDataRegisto(Instant.now());
        newAluno.setNomeCompleto(aluno.getNomeCompleto());
        newAluno.setDataNascimento(aluno.getDataNascimento());
        newAluno.setEndereco(aluno.getEndereco());
        newAluno.setReligiao(aluno.getReligiao());
        newAluno.setNomeDaMae(aluno.getNomeDaMae());
        newAluno.setNomeDoPai(aluno.getNomeDoPai());
        newAluno.setSexo(aluno.getSexo());
        newAluno.setDistritoNascimento(distrito);
        newAluno.setEstado(estado);
        newAluno.setNumeroTelefonePrincipal(aluno.getNumeroTelefonePrincipal());
        newAluno.setBilheteIdentificacao(aluno.getBilheteIdentificacao());
        newAluno.setEscolaAnterior(aluno.getEscolaAnterior());
        newAluno.setGrupoSanguineo(aluno.getGrupoSanguineo());

        logger.info("Aluno criado: {} {}" , newAluno.getDataRegisto() , newAluno.getDataNascimento());
        logger.info("Salvando o aluno...");
        alunoRepository.save(newAluno);
        log.info("Aluno salvo com sucesso: {}", newAluno);

        return newAluno;
    }


    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);
    }

    private Estado fectchEstado(String estado) {
        return estadoService.findEstado(estado) ;
    }


    @Transactional
    public Aluno update(Long id, Aluno alunoAtualizado) {

        Optional<Aluno> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isEmpty()) {
            throw new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + id);
        }

        validarDadosObrigatorios(alunoAtualizado);
        validarComprimentoMinimo(alunoAtualizado);
        validarReligiao(alunoAtualizado);
        validarSexo(alunoAtualizado);
        validarEndereco(alunoAtualizado);
        validarDataNascimento(alunoAtualizado);
        validarDataRegisto(alunoAtualizado);

        var alunoExistente = alunoOptional.get();


        alunoExistente.setNomeCompleto(alunoAtualizado.getNomeCompleto());
        alunoExistente.setNomeDoPai(alunoAtualizado.getNomeDoPai());
        alunoExistente.setNomeDaMae(alunoAtualizado.getNomeDaMae());
        alunoExistente.setReligiao(alunoAtualizado.getReligiao());
        alunoExistente.setSexo(alunoAtualizado.getSexo());
        alunoExistente.setEndereco(alunoAtualizado.getEndereco());
        alunoExistente.setDataNascimento(alunoAtualizado.getDataNascimento());
        alunoExistente.setDataRegisto(alunoAtualizado.getDataRegisto());
        alunoExistente.setEstado(alunoAtualizado.getEstado());
        alunoExistente.setDistritoNascimento(alunoAtualizado.getDistritoNascimento());
        alunoExistente.setEscolaAnterior(alunoAtualizado.getEscolaAnterior());
        alunoExistente.setGrupoSanguineo(alunoAtualizado.getGrupoSanguineo());

        return alunoRepository.save(alunoExistente);
    }

    @Transactional
    public void delete(Long id) {
        
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + id);
        }

        alunoRepository.deleteById(id);
    }

    private void validarDadosObrigatorios(Aluno aluno) {
        if (aluno.getNomeCompleto() == null || aluno.getNomeCompleto().isBlank()) {
            throw new BadRequestException("Nome do aluno " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getNomeDoPai() == null || aluno.getNomeDoPai().isBlank()) {
            throw new BadRequestException("Nome do pai do aluno " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getNomeDaMae() == null || aluno.getNomeDaMae().isBlank()) {
            throw new BadRequestException("Nome da mãe do aluno " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getReligiao() == null || aluno.getReligiao().isBlank()) {
            throw new BadRequestException("A religião do aluno " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getSexo() == null || aluno.getSexo().isBlank()) {
            throw new BadRequestException("O sexo " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getEndereco() == null || aluno.getEndereco().isBlank()) {
            throw new BadRequestException("O Endereco do aluno " + NOME_VAZIO_MESSAGE);
        }
        if (aluno.getDataNascimento() == null) {
            throw new BadRequestException(DATA_NASCIMENTO_VAZIA_MESSAGE);
        }
        if (aluno.getDataRegisto() == null) {
            throw new BadRequestException(DATA_NASCIMENTO_VAZIA_MESSAGE);
        }
    }

    private void validarComprimentoMinimo(Aluno aluno) {
        if (aluno.getNomeCompleto().length() < 6) {
            throw new BadRequestException("Nome do aluno " + NOME_CURTO_MESSAGE);
        }
        if (aluno.getNomeDoPai().length() < 6) {
            throw new BadRequestException("Nome do pai do aluno " + NOME_CURTO_MESSAGE);
        }
        if (aluno.getNomeDaMae().length() < 6) {
            throw new BadRequestException("Nome da mãe do aluno " + NOME_CURTO_MESSAGE);
        }
        if (aluno.getEndereco().length() < 20) {
            throw new BadRequestException("Endereco do aluno " + NOME_CURTO_MESSAGE);
        }
    }

    private void validarReligiao(Aluno aluno) {
        if (aluno.getReligiao().length() < 4) {
            throw new BadRequestException(RELIGIAO_CURTO_MESSAGE);
        }
    }

    private void validarSexo(Aluno aluno) {
        if (!aluno.getSexo().equalsIgnoreCase("Masculino") && !aluno.getSexo().equalsIgnoreCase("Feminino")) {
            throw new IllegalArgumentException("O sexo só pode ser \"Masculino\" ou \"Feminino\"");
        }
    }

    private void validarEndereco(Aluno aluno) {
        if (aluno.getEndereco().length() < 20) {
            throw new BadRequestException("Endereco do aluno " + NOME_CURTO_MESSAGE);
        }
    }

    private void validarDataNascimento(Aluno aluno) {
        if ((aluno.getDataNascimento().isAfter(LocalDate.now()))) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);
        }
        if (calcularIdade(converterDataParaString(aluno.getDataNascimento())) < 5) {
            throw new BadRequestException("O aluno deve ter pelo menos 5 anos de idade");
        }
    }

    private void validarDataRegisto(Aluno aluno) {
        if (aluno.getDataRegisto().isAfter(Instant.from((LocalDate.now())))) {
            throw new BadRequestException(DATA_INVALIDA_MESSAGE);
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
        return usernames.get(0) ;
    }
}
