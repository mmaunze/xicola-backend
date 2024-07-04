/*
package com.xicola.xicola.conf;

import com.xicola.xicola.model.Provider;
import com.xicola.xicola.model.Role;
import com.xicola.xicola.model.RoleName;
import com.xicola.xicola.model.Utilizador;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.xicola.xicola.repository.UtilizadorRepository;

@Service
@Configuration
@AllArgsConstructor
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunnerUser(UtilizadorRepository repository){
        Role role1 = new Role(RoleName.ADMINISTRATOR);
        Role role2 = new Role(RoleName.STUDANT);
        Role role3 = new Role(RoleName.TEACHER);
        // Criando uma lista de roles
        List<Role> roles = Arrays.asList(role1, role2, role3);
        return args -> {
            Utilizador u4 = new Utilizador("mmaunze", (new BCryptPasswordEncoder().encode("mmaunze")), roles, true, Provider.LOCAL);

            repository.saveAll(
                    List.of( u3,u4,u5)
            );
        };
    }
}
*/