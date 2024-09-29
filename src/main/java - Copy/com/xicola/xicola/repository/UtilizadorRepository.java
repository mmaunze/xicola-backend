package mz.co.mefemasys.xicola_backend.repository;

import mz.co.mefemasys.xicola_backend.models.Utilizador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Utilizador.
 */
@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {

    Optional<Utilizador> findByUsername(String username);


    @Query("SELECT u FROM Utilizador u WHERE u.estado.id = :estado")
    List<Utilizador> findByEstadoId(Long estado);

    @Query("SELECT u FROM Utilizador u WHERE u.username = :username")
    public Utilizador getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM Utilizador u WHERE u.id = :id")
    public Utilizador getUserByIdUser(@Param("id") Integer id);


}
