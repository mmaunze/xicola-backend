package com.xicola.xicola.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.PautaFinal;

@Repository
public interface PautaFinalRepository extends JpaRepository<PautaFinal, Long> {

}
