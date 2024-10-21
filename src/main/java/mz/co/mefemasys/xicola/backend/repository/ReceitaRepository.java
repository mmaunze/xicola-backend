package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Receita;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

}
