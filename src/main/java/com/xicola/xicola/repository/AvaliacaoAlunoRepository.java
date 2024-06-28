package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.AvaliacaoAluno;

@Repository
public interface AvaliacaoAlunoRepository extends JpaRepository<AvaliacaoAluno, Long> {

}
