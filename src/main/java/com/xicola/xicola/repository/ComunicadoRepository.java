package com.xicola.xicola.repository;

import com.xicola.xicola.model.Comunicado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Integer> {

}
