package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.FuncionarioRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Data
public class FuncionarioService implements MetodosGerais {

    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionario não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";
    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";
    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do Funcionario curto demais";
    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento do Funcionario não pode estar vazia";
    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";

    private final FuncionarioRepository funcionarioRepository;
    private final EstadoRepository estadoRepository;
    private final UtilizadorRepository utilizadorRepository;

    @Transactional(readOnly = true)
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FUNCIONARIO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    @Transactional
    public Funcionario create(Funcionario funcionario) {
        // Obtém o estado "Activo" da base de dados
        var estadoOptional = estadoRepository.findEstado("Activo");
        // Verifica se o estado foi encontrado
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Realiza as validações necessárias
        validarDadosObrigatorios(funcionario);
        validarComprimentoMinimo(funcionario);
        validarReligiao(funcionario);
        validarSexo(funcionario);
        validarEndereco(funcionario);
        validarDataNascimento(funcionario);
        validarEmail(funcionario);
        validarDataContracto(funcionario);
        validarTelefone(funcionario);

        // Define o estado no aluno
        funcionario.setEstado(estado);

        // Gerar e definir o username único para o utilizador
        var utilizador = new Utilizador();
        utilizador.setId(Long.parseLong(gerarId()));

        // Gera a lista de usernames possíveis a partir do nome completo do professor
        List<String> usernames = gerarUsernames(funcionario.getNomeCompleto());

        var usernameFinal = gerarUsernameUnico(usernames);

        utilizador.setUsername(usernameFinal);

        // Configurar o Utilizador no Professor
        utilizador.setPassword(funcionario.getUtilizador().getUsername()); // Pode ser qualquer senha padrão

        utilizadorRepository.save(utilizador);

        funcionario.setUtilizador(utilizador);
        funcionario.setId(utilizador.getId());

        // Salva o Funcionario no banco de dados
        return funcionarioRepository.save(funcionario);
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
        funcionarioExistente.setTipoFuncionario(funcionarioAtualizado.getTipoFuncionario());
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
        if (funcionario.getNumeroTelefoneAlternativo() != null &&
                String.valueOf(funcionario.getNumeroTelefoneAlternativo()).length() != 9) {
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

}
