package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Professor;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.ProfessorRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class ProfessorService implements MetodosGerais {

    private static final String PROFESSOR_NOT_FOUND_MESSAGE = "Professor não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome não pode estar vazio";
    private static final String NOME_CURTO_MESSAGE = "Nome curto demais";
    private static final String RELIGIAO_CURTO_MESSAGE = "Nome da religião do professor curto demais";
    private static final String DATA_NASCIMENTO_VAZIA_MESSAGE = "A data de nascimento do professor não pode estar vazia";
    private static final String DATA_INVALIDA_MESSAGE = "Data Inválida";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";


    private final ProfessorRepository professorRepository;
    private final EstadoRepository estadoRepository;
    private final UtilizadorRepository utilizadorRepository;

    @Transactional(readOnly = true)
    public Professor findById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROFESSOR_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Transactional
    public Professor create(Professor professor) {
        var estado = obterEstadoActivo();

        validarProfessor(professor);

        var utilizador = criarUtilizadorParaProfessor(professor, estado);

        professor.setUtilizador(utilizador);
        professor.setId(utilizador.getId());
        professor.setEstado(estado);

        return professorRepository.save(professor);
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

    private Estado obterEstadoActivo() {
        return estadoRepository.findEstado("Activo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));
    }

    private Utilizador criarUtilizadorParaProfessor(Professor professor, Estado estado) {
        var utilizador = new Utilizador();
        utilizador.setId(gerarId());

        List<String> usernames = gerarUsernames(professor.getNomeCompleto());
        utilizador.setUsername(gerarUsernameUnico(usernames));
        utilizador.setPassword(professor.getUtilizador().getUsername());
    //    utilizador.setEstado(estado);
   //     utilizador.setTipoUtilizador(professor.getUtilizador().getTipoUtilizador());

        utilizadorRepository.save(utilizador);
        return utilizador;
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
        if (professor.getNumeroTelefoneAlternativo() != null &&
                String.valueOf(professor.getNumeroTelefoneAlternativo()).length() != 9) {
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
}

