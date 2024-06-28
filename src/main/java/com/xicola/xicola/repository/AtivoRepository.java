package com.xicola.xicola.repository;

import com.xicola.xicola.model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Integer> {

}