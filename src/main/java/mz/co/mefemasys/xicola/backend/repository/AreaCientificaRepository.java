package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.AreaCientifica;
import mz.co.mefemasys.xicola.backend.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaCientificaRepository extends JpaRepository<AreaCientifica, Long> {

    @Query("SELECT ac FROM AreaCientifica ac WHERE ac.descricao = :descricao")
    Optional<AreaCientifica> findArea(@Param("descricao") String descricao);

}
