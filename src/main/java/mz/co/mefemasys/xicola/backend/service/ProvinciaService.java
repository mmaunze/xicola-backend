package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Provincia;
import mz.co.mefemasys.xicola.backend.repository.ProvinciaRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import mz.co.mefemasys.xicola.backend.utils.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProvinciaService {

    private static final String PROVINCIA_NOT_FOUND_MESSAGE = "Provincia não encontrada com o ID: ";

    private static final Logger LOG = Logger.getLogger(ProvinciaService.class.getName());

    private final ProvinciaRepository provinciaRepository;

    @Transactional(readOnly = true)
    public Provincia findById(Long id) {
        return provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVINCIA_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public List<Provincia> findAll() {
        return provinciaRepository.findAll();

    }

    @Transactional
    public Provincia create(Provincia provincia) {
        validarNomeProvincia(provincia.getNomeProvincia());

        return provinciaRepository.save(provincia);

    }

    @Transactional
    public Provincia update(Long id, Provincia provinciaAtualizada) {
        var provinciaExistente = provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVINCIA_NOT_FOUND_MESSAGE + id));

        validarNomeProvincia(provinciaAtualizada.getNomeProvincia());

        provinciaExistente.setNomeProvincia(provinciaAtualizada.getNomeProvincia());

        return provinciaRepository.save(provinciaExistente);

    }

    @Transactional
    public void delete(Long id) {

        provinciaRepository.deleteById(id);

    }

    private void validarNomeProvincia(String nomeProvincia) {
        if (nomeProvincia == null || nomeProvincia.trim().isEmpty()) {
            throw new BadRequestException("O nome da província não pode estar vazio.");

        }

        if (provinciaRepository.findByNomeProvincia(nomeProvincia).isPresent()) {
            throw new BadRequestException("Já existe uma província com o nome fornecido: " + nomeProvincia);

        }
    }

    public Provincia findProvincia(String provincia) {
        return provinciaRepository.findByNomeProvincia(provincia)
                .orElseThrow(() -> new ResourceNotFoundException(PROVINCIA_NOT_FOUND_MESSAGE + provincia));

    }
}
