package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.repository.AlunoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
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
    private final UtilizadorRepository utilizadorRepository;

    @Transactional(readOnly = true)
    public Aluno findById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ALUNO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional
    public Aluno create(Aluno aluno) {
        // Obtém o estado "Activo" da base de dados
        var estadoOptional = estadoRepository.findEstado("Activo");
        // Verifica se o estado foi encontrado
        var estado = estadoOptional
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

        // Realiza as validações necessárias
        validarDadosObrigatorios(aluno);
        validarComprimentoMinimo(aluno);
        validarReligiao(aluno);
        validarSexo(aluno);
        validarEndereco(aluno);
        validarDataNascimento(aluno);
        validarDataRegisto(aluno);

        // Define o estado no aluno
        aluno.setEstado(estado);

        // Gerar e definir o username único para o utilizador
        var utilizador = new Utilizador();
        utilizador.setId(Long.parseLong(gerarId()));

        // Gera a lista de usernames possíveis a partir do nome completo do professor
        List<String> usernames = gerarUsernames(aluno.getNomeCompleto());

        var usernameFinal = gerarUsernameUnico(usernames);

        utilizador.setUsername(usernameFinal);

        // Configurar o Utilizador no Professor
        utilizador.setPassword(aluno.getUtilizador().getUsername()); // Pode ser qualquer senha padrão

        utilizadorRepository.save(utilizador);

        aluno.setUtilizador(utilizador);
        aluno.setId(utilizador.getId());

        // Salva o aluno no banco de dados
        return alunoRepository.save(aluno);
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
