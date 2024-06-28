package com.xicola.xicola.repository;

import com.xicola.xicola.model.Veiculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Veiculo.
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v WHERE v.estado.id = :estado")
    List<Veiculo> findByEstadoId(Long estado);

}
