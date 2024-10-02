package mz.co.mefemasys.xicola.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mz.co.mefemasys.xicola.backend.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    @Query("SELECT e FROM Estado e WHERE e.descricao = :descricao")
    Optional<Estado> findEstado(@Param("descricao") String descricao);

    @Query("SELECT e FROM Estado e WHERE e.tipoEstado.descricao = :descricao")
    List<Estado> findByTipoEstado(String descricao);
}
