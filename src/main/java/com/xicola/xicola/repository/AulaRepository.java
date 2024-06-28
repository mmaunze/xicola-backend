package com.xicola.xicola.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {




}
