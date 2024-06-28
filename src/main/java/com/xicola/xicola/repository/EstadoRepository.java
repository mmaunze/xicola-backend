package com.xicola.xicola.repository;

import com.xicola.xicola.model.Estado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Query("SELECT e FROM Estado e WHERE e.descricao = :descricao")
    Optional<Estado> findEstado(@Param("descricao") String descricao);

}
