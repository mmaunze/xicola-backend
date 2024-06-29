package com.xicola.xicola.repository;

import com.xicola.xicola.model.Departamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    @Query("SELECT d FROM Departamento d WHERE d.descricao = :descricao")
    Optional<Departamento> findByDescricao(@Param("descricao") String descricao);

    @Query("SELECT d FROM Departamento d WHERE d.sigla = :sigla")
    Optional<Departamento> findBySigla(@Param("sigla") String sigla);

}
