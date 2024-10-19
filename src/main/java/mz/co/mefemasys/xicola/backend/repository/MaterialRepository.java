package mz.co.mefemasys.xicola.backend.repository;

import java.util.Optional;
import mz.co.mefemasys.xicola.backend.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT m FROM Material m WHERE m.nomeMaterial = :nomeMaterial")
    Optional<Material> findByNomeMaterial(@Param("nomeMaterial") String nomeMaterial);

}
