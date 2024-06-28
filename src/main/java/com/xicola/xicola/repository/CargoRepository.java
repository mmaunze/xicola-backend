package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
