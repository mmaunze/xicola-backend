package mz.co.mefemasys.xicola_backend.conf;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mz.co.mefemasys.xicola_backend.models.Provider;
import mz.co.mefemasys.xicola_backend.models.Role;
import mz.co.mefemasys.xicola_backend.models.RoleName;
import mz.co.mefemasys.xicola_backend.models.Utilizador;
import mz.co.mefemasys.xicola_backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola_backend.service.EstadoService;
import mz.co.mefemasys.xicola_backend.service.TipoPessoaService;

import lombok.RequiredArgsConstructor;

@Service
@Configuration
// @AllArgsConstructor
@RequiredArgsConstructor
public class UserConfig {
    private final TipoPessoaService tipoPessoaService;
    private final EstadoService estadoService;
    private final UtilizadorRepository utilizadorRepository;

    @Bean
    CommandLineRunner commandLineRunnerUser() {
        Role role1 = new Role(RoleName.ADMINISTRATOR);
        Role role2 = new Role(RoleName.ALUNO);
        Role role3 = new Role(RoleName.PROFESSOR);
        var admin = tipoPessoaService.findDestinatario("Admin");
        var activo = estadoService.findEstado("Activo");

        List<Role> roles = Arrays.asList(role1, role2, role3);

        return args -> {
            Utilizador meldo = new Utilizador();
            meldo.setId(Long.valueOf("20200401053"));
            meldo.setUsername("mmaunze");
            meldo.setSenha(new BCryptPasswordEncoder().encode("Pa$$w0rd"));
            meldo.setTipoUtilizador(admin);
            meldo.setEstado(activo);
            meldo.setRoles(roles);
            meldo.setEnabled(true);
            meldo.setProvider(Provider.LOCAL);
            utilizadorRepository.save(meldo);
        };
    }
}