package mz.co.mefemasys.xicola.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Estado;
import mz.co.mefemasys.xicola.backend.models.Funcionario;
import mz.co.mefemasys.xicola.backend.models.Veiculo;
import mz.co.mefemasys.xicola.backend.repository.EstadoRepository;
import mz.co.mefemasys.xicola.backend.repository.FuncionarioRepository;
import mz.co.mefemasys.xicola.backend.repository.VeiculoRepository;
import mz.co.mefemasys.xicola.backend.utils.MetodosGerais;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Data
public class VeiculoService implements MetodosGerais {

    private static final String VEICULO_NOT_FOUND_MESSAGE = "Veículo não encontrado com o ID: ";

    private static final String MODELO_VAZIO_MESSAGE = "O modelo do veículo não pode estar vazio";

    private static final String MODELO_CURTO_MESSAGE = "Modelo do veículo muito curto";

    private static final String ESTADO_CURTO_MESSAGE = "Nome do estado do veículo curto demais";

    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado";

    private static final Logger LOG = Logger.getLogger(VeiculoService.class.getName());

    private final VeiculoRepository veiculoRepository;

    private final EstadoRepository estadoRepository;

    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(VEICULO_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();

    }

    @Transactional
    public Veiculo create(Veiculo veiculo) {
        var estado = obterEstadoActivo();

        validarVeiculo(veiculo);

        var motorista = obterMotoristaAleatorio();

        veiculo.setEstado(estado);

        veiculo.setMotorista(motorista);

        return veiculoRepository.save(veiculo);

    }

    @Transactional
    public Veiculo update(Long id, Veiculo veiculoAtualizado) {
        var veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(VEICULO_NOT_FOUND_MESSAGE + id));

        validarVeiculo(veiculoAtualizado);

        atualizarVeiculo(veiculoExistente, veiculoAtualizado);

        return veiculoRepository.save(veiculoExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException(VEICULO_NOT_FOUND_MESSAGE + id);

        }
        veiculoRepository.deleteById(id);

    }

    private void validarVeiculo(Veiculo veiculo) {
        validarModelo(veiculo);

        validarEstado(veiculo);

    }

    private void validarModelo(Veiculo veiculo) {
        if (veiculo.getCodigoVeiculo() == null || veiculo.getCodigoVeiculo().isBlank()) {
            throw new BadRequestException("Modelo do veículo " + MODELO_VAZIO_MESSAGE);

        }
        if (veiculo.getCodigoVeiculo().length() < 3) {
            throw new BadRequestException("Modelo do veículo " + MODELO_CURTO_MESSAGE);

        }
    }

    private void validarEstado(Veiculo veiculo) {
        if (veiculo.getEstado().getDescricao().length() < 4) {
            throw new BadRequestException(ESTADO_CURTO_MESSAGE);

        }
    }

    private Estado obterEstadoActivo() {
        return estadoRepository.findEstado("Activo")
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NOT_FOUND_MESSAGE));

    }

    private Funcionario obterMotoristaAleatorio() {
        var motoristas = funcionarioRepository.findAll();

        if (motoristas.isEmpty()) {
            throw new ResourceNotFoundException("Não há motoristas disponíveis.");

        }
        return motoristas.get(new Random().nextInt(motoristas.size()));

    }

    private void atualizarVeiculo(Veiculo veiculoExistente, Veiculo veiculoAtualizado) {
        veiculoExistente.setCodigoVeiculo(veiculoAtualizado.getCodigoVeiculo());

        veiculoExistente.setMarcaVeiculo(veiculoAtualizado.getMarcaVeiculo());

        veiculoExistente.setEstado(veiculoAtualizado.getEstado());

        veiculoExistente.setMotorista(veiculoAtualizado.getMotorista());

    }
}
