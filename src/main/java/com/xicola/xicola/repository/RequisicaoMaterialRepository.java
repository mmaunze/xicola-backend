package com.xicola.xicola.repository;

import com.xicola.xicola.model.RequisicaoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoMaterialRepository extends JpaRepository<RequisicaoMaterial, Long> {

}
