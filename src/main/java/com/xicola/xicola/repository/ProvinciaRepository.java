package com.xicola.xicola.repository;

import com.xicola.xicola.model.Provincia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {


    Optional<Provincia> findByNomeProvincia(String nomeProvincia);


}
