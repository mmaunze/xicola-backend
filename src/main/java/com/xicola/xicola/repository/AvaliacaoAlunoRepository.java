package com.xicola.xicola.repository;

import com.xicola.xicola.model.AvaliacaoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoAlunoRepository extends JpaRepository<AvaliacaoAluno, Long> {

}
