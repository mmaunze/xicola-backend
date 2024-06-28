package com.xicola.xicola.repository;

import com.xicola.xicola.model.PresencasAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresencasAlunoRepository extends JpaRepository<PresencasAluno, Integer> {


}
