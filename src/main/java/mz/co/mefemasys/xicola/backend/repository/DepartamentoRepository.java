package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.descricao = :descricao")
    Optional<Departamento> findDepartamento(@Param("descricao") String descricao);

    @Query("SELECT d FROM Departamento d WHERE d.sigla = :sigla")
    Optional<Departamento> findBySigla(@Param("sigla") String sigla);

}
