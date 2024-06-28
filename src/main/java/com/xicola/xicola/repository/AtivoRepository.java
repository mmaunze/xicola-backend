package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Ativo;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Integer> {

}