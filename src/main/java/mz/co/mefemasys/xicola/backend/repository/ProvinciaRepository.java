package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Provincia;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Optional<Provincia> findByNomeProvincia(String nomeProvincia);

}
