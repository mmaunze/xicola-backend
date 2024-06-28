package com.xicola.xicola.repository;

import com.xicola.xicola.model.ManutencaoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutencaoAtivoRepository extends JpaRepository<ManutencaoAtivo, Integer> {


}
