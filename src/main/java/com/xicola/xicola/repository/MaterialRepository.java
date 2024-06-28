package com.xicola.xicola.repository;

import java.util.List;
import java.util.Optional;

import com.xicola.xicola.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT m FROM Material m WHERE m.nomeMaterial = :nomeMaterial")
    Optional<Material> findByNomeMaterial(@Param("nomeMaterial") String nomeMaterial);

}
