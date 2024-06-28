package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

}
