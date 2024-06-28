package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Distrito;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Long> {

}
