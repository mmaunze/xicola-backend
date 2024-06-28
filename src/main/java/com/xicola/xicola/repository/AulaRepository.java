package com.xicola.xicola.repository;

import com.xicola.xicola.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {

   
}
