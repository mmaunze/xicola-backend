package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Ativo;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.models.ManutencaoAtivo;
import mz.co.mefemasys.xicola.backend.repository.AtivoRepository;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.FuncionarioRepository;
import mz.co.mefemasys.xicola.backend.repository.ManutencaoAtivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoAtivoService {

    private static final String MANUTENCAO_ATIVO_NOT_FOUND_MESSAGE = "Manutenção de ativo não encontrada com o ID: ";
    private static final String DESCRICAO_VAZIA_MESSAGE = "A descrição não pode estar vazia";
    private static final String DATA_MANUTENCAO_INVALIDA_MESSAGE = "A data de manutenção não pode estar no futuro";
    private static final String CUSTO_INVALIDO_MESSAGE = "O custo da manutenção deve ser maior ou igual a zero";
    private static final String ATIVO_NOT_FOUND_MESSAGE = "Ativo não encontrado com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o nome: ";
    private static final String FUNCIONARIO_NOT_FOUND_MESSAGE = "Funcionário não encontrado com o ID: ";

    private final ManutencaoAtivoRepository manutencaoAtivoRepository;
    private final AtivoRepository ativoRepository;
    private final EstadoRepository estadoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public ManutencaoAtivo findById(Long id) {
        return manutencaoAtivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MANUTENCAO_ATIVO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<ManutencaoAtivo> findAll() {
        return manutencaoAtivoRepository.findAll();
    }

    @Transactional
    public ManutencaoAtivo create(ManutencaoAtivo manutencaoAtivo) {
        validarManutencaoAtivo(manutencaoAtivo);

        var estado = obterEstadoActivo();
        manutencaoAtivo.setEstado(estado);

        return manutencaoAtivoRepository.save(manutencaoAtivo);
    }

    @Transactional
    public ManutencaoAtivo update(Long id, ManutencaoAtivo manutencaoAtivoAtualizado) {
        var manutencaoAtivoExistente = manutencaoAtivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MANUTENCAO_ATIVO_NOT_FOUND_MESSAGE + id));

        validarManutencaoAtivo(manutencaoAtivoAtualizado);

        atualizarManutencaoAtivo(manutencaoAtivoExistente, manutencaoAtivoAtualizado);

        return manutencaoAtivoRepository.save(manutencaoAtivoExistente);
    }

    @Transactional
    public void delete(Long id) {
        if (!manutencaoAtivoRepository.existsById(id)) {
            throw new ResourceNotFoundException(MANUTENCAO_ATIVO_NOT_FOUND_MESSAGE + id);
        }
        manutencaoAtivoRepository.deleteById(id);
    }

    private void validarManutencaoAtivo(ManutencaoAtivo manutencaoAtivo) {
        validarDadosObrigatorios(manutencaoAtivo);
        validarDataManutencao(manutencaoAtivo);
        validarCusto(manutencaoAtivo);
        validarAtivo(manutencaoAtivo);
        validarResponsavel(manutencaoAtivo);
    }

    private void validarDadosObrigatorios(ManutencaoAtivo manutencaoAtivo) {
        if (manutencaoAtivo.getDescricao() == null || manutencaoAtivo.getDescricao().isBlank()) {
            throw new BadRequestException(DESCRICAO_VAZIA_MESSAGE);
        }
    }

    private void validarDataManutencao(ManutencaoAtivo manutencaoAtivo) {

        if (manutencaoAtivo.getDataManutencao().isAfter(LocalDate.now())) {
            throw new BadRequestException(DATA_MANUTENCAO_INVALIDA_MESSAGE);
        }
    }

    private void validarCusto(ManutencaoAtivo manutencaoAtivo) {
        if (manutencaoAtivo.getCusto() != null && manutencaoAtivo.getCusto().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(CUSTO_INVALIDO_MESSAGE);
        }
    }

    private void validarAtivo(ManutencaoAtivo manutencaoAtivo) {
        Ativo ativo = ativoRepository.findById(manutencaoAtivo.getAtivo().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ATIVO_NOT_FOUND_MESSAGE + manutencaoAtivo.getAtivo().getId()));
        manutencaoAtivo.setAtivo(ativo);
    }

    private void validarResponsavel(ManutencaoAtivo manutencaoAtivo) {
        Funcionario responsavel = funcionarioRepository.findById(manutencaoAtivo.getResponsavel().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        FUNCIONARIO_NOT_FOUND_MESSAGE + manutencaoAtivo.getResponsavel().getId()));
        manutencaoAtivo.setResponsavel(responsavel);
    }

    private void atualizarManutencaoAtivo(ManutencaoAtivo manutencaoAtivoExistente,
                                          ManutencaoAtivo manutencaoAtivoAtualizado) {
        manutencaoAtivoExistente.setDescricao(manutencaoAtivoAtualizado.getDescricao());
        manutencaoAtivoExistente.setDataManutencao(manutencaoAtivoAtualizado.getDataManutencao());
        manutencaoAtivoExistente.setCusto(manutencaoAtivoAtualizado.getCusto());
        manutencaoAtivoExistente.setAtivo(manutencaoAtivoAtualizado.getAtivo());
        manutencaoAtivoExistente.setResponsavel(manutencaoAtivoAtualizado.getResponsavel());
    }

    private Estado obterEstadoActivo() {
        return estadoRepository.findEstado("Activo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));
    }
}
