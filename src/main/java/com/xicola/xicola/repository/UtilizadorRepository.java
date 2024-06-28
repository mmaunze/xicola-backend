package com.xicola.xicola.repository;

import com.xicola.xicola.model.Utilizador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Utilizador.
 */
@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {

    Optional<Utilizador> findByUsername(String username);


    @Query("SELECT u FROM Utilizador u WHERE u.estado.id = :estado")
    List<Utilizador> findByEstadoId(Long estado);


}
