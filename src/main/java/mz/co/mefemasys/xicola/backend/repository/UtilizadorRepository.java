package mz.co.mefemasys.xicola.backend.repository;

import java.util.Optional;

import mz.co.mefemasys.xicola.backend.models.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
  Optional<Utilizador> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
