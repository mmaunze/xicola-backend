package mz.co.mefemasys.xicola.backend.service;

import mz.co.mefemasys.xicola.backend.models.Fornecedor;
import mz.co.mefemasys.xicola.backend.repository.FornecedorRepository;
import mz.co.mefemasys.xicola.backend.service.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.service.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private static final String FORNECEDOR_NOT_FOUND_MESSAGE = "Fornecedor não encontrado com o ID: ";
    private static final String NOME_VAZIO_MESSAGE = "O nome do fornecedor não pode estar vazio";
    private static final String ENDERECO_CURTO_MESSAGE = "Endereço do fornecedor muito curto";
    private static final String TELEFONE_INVALIDO_MESSAGE = "Número de telefone inválido";
    private static final String EMAIL_INVALIDO_MESSAGE = "Endereço de email inválido";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    private final FornecedorRepository fornecedorRepository;

    @Transactional(readOnly = true)
    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORNECEDOR_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    @Transactional
    public Fornecedor create(Fornecedor fornecedor) {
        validarFornecedorParaCriacao(fornecedor);
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public Fornecedor update(Long id, Fornecedor fornecedorAtualizado) {
        var fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORNECEDOR_NOT_FOUND_MESSAGE + id));

        validarFornecedorParaAtualizacao(fornecedorAtualizado);

        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setEndereco(fornecedorAtualizado.getEndereco());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());
        fornecedorExistente.setDescricao(fornecedorAtualizado.getDescricao());
        // Aqui você pode adicionar outras atualizações conforme necessário

        return fornecedorRepository.save(fornecedorExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new ResourceNotFoundException(FORNECEDOR_NOT_FOUND_MESSAGE + id);
        }
        fornecedorRepository.deleteById(id);
    }

    private void validarFornecedorParaCriacao(Fornecedor fornecedor) {
        validarNome(fornecedor.getNome());
        validarEndereco(fornecedor.getEndereco());
        validarTelefone(fornecedor.getTelefone());
        validarEmail(fornecedor.getEmail());
        // Aqui você pode adicionar mais validações conforme necessário
    }

    private void validarFornecedorParaAtualizacao(Fornecedor fornecedor) {
        if (fornecedor.getNome() != null) {
            validarNome(fornecedor.getNome());
        }
        if (fornecedor.getEndereco() != null) {
            validarEndereco(fornecedor.getEndereco());
        }
        if (fornecedor.getTelefone() != null) {
            validarTelefone(fornecedor.getTelefone());
        }
        if (fornecedor.getEmail() != null) {
            validarEmail(fornecedor.getEmail());
        }
        // Aqui você pode adicionar mais validações conforme necessário
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new BadRequestException(NOME_VAZIO_MESSAGE);
        }
        // Aqui você pode adicionar outras validações para o nome
    }

    private void validarEndereco(String endereco) {
        if (endereco != null && endereco.length() < 10) {
            throw new BadRequestException(ENDERECO_CURTO_MESSAGE);
        }
        // Aqui você pode adicionar outras validações para o endereço
    }

    private void validarTelefone(String telefone) {
        if (telefone != null && !telefone.matches("\\d{9,15}")) {
            throw new BadRequestException(TELEFONE_INVALIDO_MESSAGE);
        }
        // Aqui você pode adicionar outras validações para o telefone
    }

    private void validarEmail(String email) {
        if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException(EMAIL_INVALIDO_MESSAGE);
        }
        // Aqui você pode adicionar outras validações para o email
    }
}
