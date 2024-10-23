package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {

    @Query("SELECT tp FROM TipoPagamento tp WHERE tp.descricao = :descricao")
    Optional<TipoPagamento> findTipo(@Param("descricao") String descricao);
}
