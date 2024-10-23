package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.CategoriaFinanceira;
import mz.co.mefemasys.xicola.backend.repository.CategoriaFinanceiraRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaFinanceiraService {

    private static final String CATEGORIA_FINANCEIRA_NOT_FOUND_MESSAGE = "Categoria financeira não encontrada com o ID: ";

    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição da categoria financeira não pode estar vazia";

    private final CategoriaFinanceiraRepository categoriaFinanceiraRepository;

    @Transactional(readOnly = true)
    public CategoriaFinanceira findById(Long id) {
        return categoriaFinanceiraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIA_FINANCEIRA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public Iterable<CategoriaFinanceira> findAll() {
        return categoriaFinanceiraRepository.findAll();

    }

    @Transactional
    public CategoriaFinanceira create(CategoriaFinanceira categoriaFinanceira) {
        validarDadosObrigatorios(categoriaFinanceira);

        return categoriaFinanceiraRepository.save(categoriaFinanceira);

    }

    @Transactional
    public CategoriaFinanceira update(Long id, CategoriaFinanceira categoriaFinanceiraAtualizada) {
        var categoriaFinanceiraOptional = categoriaFinanceiraRepository.findById(id);

        if (categoriaFinanceiraOptional.isEmpty()) {
            throw new ResourceNotFoundException(CATEGORIA_FINANCEIRA_NOT_FOUND_MESSAGE + id);

        }

        var categoriaFinanceiraExistente = categoriaFinanceiraOptional.get();

        validarDadosObrigatorios(categoriaFinanceiraAtualizada);

        categoriaFinanceiraExistente.setDescricao(categoriaFinanceiraAtualizada.getDescricao());

        return categoriaFinanceiraRepository.save(categoriaFinanceiraExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaFinanceiraRepository.existsById(id)) {
            throw new ResourceNotFoundException(CATEGORIA_FINANCEIRA_NOT_FOUND_MESSAGE + id);

        }

        categoriaFinanceiraRepository.deleteById(id);

    }

    private void validarDadosObrigatorios(CategoriaFinanceira categoriaFinanceira) {
        if (categoriaFinanceira.getDescricao() == null || categoriaFinanceira.getDescricao().isBlank()) {
            throw new ResourceNotFoundException(DESCRICAO_VAZIA_MESSAGE);

        }
    }

    public CategoriaFinanceira findCategoria(String categoria) {
        return categoriaFinanceiraRepository.findCategoria(categoria)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIA_FINANCEIRA_NOT_FOUND_MESSAGE + categoria));

    }
}
