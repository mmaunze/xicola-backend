package mz.co.mefemasys.xicola.backend.service;

import lombok.extern.slf4j.Slf4j;
import mz.co.mefemasys.xicola.backend.models.*;
import mz.co.mefemasys.xicola.backend.models.dto.AlunoDTO;
import mz.co.mefemasys.xicola.backend.payload.request.SignupRequest;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static mz.co.mefemasys.xicola.backend.models.ERole.ROLE_ESTUDANTE;

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

        // Se não for nulo, faz a busca
        return alunoRepository.findAlunosByEstado(estado.toLowerCase());
    }

    private static final Logger logger = LoggerFactory.getLogger(AlunoService.class);


    @Transactional
    public Aluno create(AlunoDTO aluno) {
        try {
            // Gerar username
            logger.info("A gerar um username único para o aluno: {}", aluno.getNomeCompleto());
            String username = gerarUsernameUnico(gerarUsernames(aluno.getNomeCompleto()));
            logger.info("Username gerado: {}", username);

            // Gerar ID
            long id = gerarId();
            logger.info("ID gerado para o utilizador: {}", id);

            // Criar utilizador
            Utilizador utilizador = new Utilizador();
            utilizador.setId(id);
            utilizador.setNome(aluno.getNomeCompleto());
            utilizador.setUsername(username);
            utilizador.setPassword(encoder.encode(username));
            utilizador.setEmail(username + "@gmail.com");
            logger.info("Utilizador preparado: {}", utilizador);

            // Buscar Role
            logger.info("A buscar o Role 'ESTUDANTE' do repositório");
            Role estudanteRole = roleRepository.findByName(ROLE_ESTUDANTE)
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ESTUDANTE' não encontrado."));
            logger.info("Role 'ESTUDANTE' encontrado");

            // Atribuir roles
            Set<Role> roles = new HashSet<>();
            roles.add(estudanteRole);
            utilizador.setRoles(roles);

            // Guardar utilizador no repositório
            try {
                logger.info("A guardar o utilizador no repositório");
                utilizadorRepository.save(utilizador);
                logger.info("Utilizador guardado com sucesso: {}", utilizador);
            } catch (Exception e) {
                logger.error("Erro ao salvar o utilizador: {}", e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Erro ao salvar o utilizador.", e);
            }

            // Buscar distrito e estado
            logger.info("A buscar o distrito de nascimento: {}", aluno.getDistritoNascimento());
            Distrito distrito = fectchDistrito(aluno.getDistritoNascimento());
            logger.info("Distrito encontrado: {}", distrito);

            logger.info("A buscar o estado 'Activo'");
            Estado estado = fectchEstado("Activo");
            logger.info("Estado encontrado: {}", estado);

            // Criar novo aluno
            var newAluno = new Aluno();
            newAluno.setId(aluno.getId());
            newAluno.setNomeCompleto(aluno.getNomeCompleto());
            newAluno.setDataRegisto(new Date().toInstant());
            newAluno.setDataNascimento(converterStringParaData(aluno.getDataNascimento()));
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

            try {
                logger.info("A guardar o novo aluno no repositório");
                return alunoRepository.save(newAluno);
            } catch (Exception e) {
                logger.error("Erro ao salvar o aluno: {}", e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Erro ao salvar o aluno.", e);
            }

        } catch (Exception e) {
            logger.error("Erro ao criar  aluno: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar aluno.", e);
        }
    }


    private Distrito fectchDistrito(String distrito) {
        return distritoService.findDistrito(distrito);
    }

    private Estado fectchEstado(String estado) {
        return estadoService.findEstado(estado) ;
    }


    @Transactional
    public Aluno update(Long id, Aluno alunoAtualizado) {

        var alunoOptional = alunoRepository.findById(id);
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

        // Outras validações específicas de atualização, se necessário

        var alunoExistente = alunoOptional.get();

        // Atualize outras propriedades conforme necessário

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
        // Verifica se o aluno existe antes de excluí-lo
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
