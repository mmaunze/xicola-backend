package mz.co.mefemasys.xicola.backend.repository;

import java.util.Optional;
import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorTrabalhoRepository extends JpaRepository<SectorTrabalho, Long> {
    
  @Query("SELECT st FROM SectorTrabalho st WHERE st.descricao = :descricao")
    Optional<SectorTrabalho> findSector(@Param("descricao") String descricao);

}
