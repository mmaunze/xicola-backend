package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.DetalheRelatorioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalheRelatorioFinanceiroRepository extends JpaRepository<DetalheRelatorioFinanceiro, Long> {

}
