package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT count(p) FROM Pagamento p WHERE p.estado.descricao = :descricao")
    Long totalEstado(@Param("descricao") String descricao);

    @Query("SELECT count(p) FROM Pagamento p WHERE p.tipoPagamento.descricao = :descricao")
    Long totalTipo(@Param("descricao") String descricao);
}
