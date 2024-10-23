package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.exceptions.BadRequestException;
import mz.co.mefemasys.xicola.backend.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.models.Distrito;
import mz.co.mefemasys.xicola.backend.repository.DistritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistritoService {

    private static final String DISTRICT_NOT_FOUND_MESSAGE = "Distrito não encontrado com o ID: %d";

    private static final String DISTRICT_NAME_REQUIRED_MESSAGE = "O nome do distrito é obrigatório";

    private final DistritoRepository distritoRepository;

    @Transactional(readOnly = true)
    public Iterable<Distrito> findAll() {
        return distritoRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Distrito findById(Long id) {
        return distritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id)));

    }

    @Transactional(readOnly = true)
    public Distrito findDistrito(String nome) {
        return distritoRepository.findDistrito(nome)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, nome)));

    }

    @Transactional(readOnly = true)
    public List<Distrito> findDistritoProvincia(String nome) {
        return distritoRepository.findDistritoProvincia(nome.toLowerCase());

    }

    @Transactional
    public Distrito create(Distrito distrito) {
        validarCamposObrigatorios(distrito);

        return distritoRepository.save(distrito);

    }

    @Transactional
    public Distrito update(Long id, Distrito distritoAtualizado) {
        validarDistritoExistente(id);

        validarCamposObrigatorios(distritoAtualizado);

        var distritoExistente = distritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id)));

        mapearDistritoAtualizado(distritoExistente, distritoAtualizado);

        return distritoRepository.save(distritoExistente);

    }

    @Transactional
    public void delete(Long id) {
        validarDistritoExistente(id);

        distritoRepository.deleteById(id);

    }

    private void validarCamposObrigatorios(Distrito distrito) {
        if (distrito.getNomeDistrito() == null || distrito.getNomeDistrito().isEmpty()) {
            throw new BadRequestException(DISTRICT_NAME_REQUIRED_MESSAGE);

        }
    }

    private void validarDistritoExistente(Long id) {
        if (!distritoRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DISTRICT_NOT_FOUND_MESSAGE, id));

        }
    }

    private void mapearDistritoAtualizado(Distrito distritoExistente, Distrito distritoAtualizado) {
        distritoExistente.setNomeDistrito(distritoAtualizado.getNomeDistrito());

    }
}
