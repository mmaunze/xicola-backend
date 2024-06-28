package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

}
