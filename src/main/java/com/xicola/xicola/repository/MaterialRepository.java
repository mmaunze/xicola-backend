package com.xicola.xicola.repository;

import com.xicola.xicola.model.Material;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT m FROM Material m WHERE m.nomeMaterial = :nomeMaterial")
    Optional<Material> findByNomeMaterial(@Param("nomeMaterial") String nomeMaterial);

}
