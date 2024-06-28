package com.xicola.xicola.repository;

import com.xicola.xicola.model.TipoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMaterialRepository extends JpaRepository<TipoMaterial, Integer> {


}
