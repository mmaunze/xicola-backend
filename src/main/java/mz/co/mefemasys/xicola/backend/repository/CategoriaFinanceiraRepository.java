package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.CategoriaFinanceira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaFinanceiraRepository extends JpaRepository<CategoriaFinanceira, Long> {

    @Query("SELECT cf FROM CategoriaFinanceira cf WHERE cf.descricao = :categoria")
    Optional<CategoriaFinanceira> findCategoria(String categoria);

}
