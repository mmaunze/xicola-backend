package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Distrito;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Long> {

  @Query("SELECT d FROM Distrito d WHERE d.nomeDistrito = :descricao")
    Optional<Distrito> findDistrito (@Param("descricao") String descricao);

  @Query("SELECT d FROM Distrito d WHERE d.provincia = :provincia")
  List<Distrito> findDistritoProvincia (@Param("provincia") String provincia);

}
