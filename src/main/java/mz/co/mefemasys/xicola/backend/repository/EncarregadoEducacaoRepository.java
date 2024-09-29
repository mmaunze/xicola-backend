package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncarregadoEducacaoRepository extends JpaRepository<EncarregadoEducacao, Long> {

}
