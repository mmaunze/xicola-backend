package com.xicola.xicola.repository;

import com.xicola.xicola.model.Distrito;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {
  @Query("SELECT d FROM Distrito d WHERE d.nomeDistrito = :descricao")
    Optional<Distrito> findDistrito (@Param("descricao") String descricao);

}
