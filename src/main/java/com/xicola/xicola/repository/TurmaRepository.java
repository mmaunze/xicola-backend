package com.xicola.xicola.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Professor;
import com.xicola.xicola.model.Turma;


@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {


}
