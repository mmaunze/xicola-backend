package mz.co.mefemasys.xicola.backend.service;

import lombok.RequiredArgsConstructor;
import mz.co.mefemasys.xicola.backend.models.Role;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RoleService {

    private static final String ROLE_NOT_FOUND_MESSAGE = "Cargo não encontrado com o ID: ";

    private static final Logger LOG = Logger.getLogger(RoleService.class.getName());

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND_MESSAGE + id));

    }

    @Transactional(readOnly = true)
    public Iterable<Role> findAll() {
        return roleRepository.findAll();

    }

    @Transactional
    public Role create(Role cargo) {

        return roleRepository.save(cargo);

    }

    @Transactional
    public Role update(Long id, Role roleAtualizado) {
        var roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            throw new ResourceNotFoundException(ROLE_NOT_FOUND_MESSAGE + id);

        }
        var roleExistente = roleOptional.get();

        roleExistente.setName(roleAtualizado.getName());

        return roleRepository.save(roleExistente);

    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException(ROLE_NOT_FOUND_MESSAGE + id);

        }

        roleRepository.deleteById(id);

    }

}
