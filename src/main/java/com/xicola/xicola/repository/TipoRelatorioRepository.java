package com.xicola.xicola.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.TipoRelatorio;


@Repository
public interface TipoRelatorioRepository extends JpaRepository<TipoRelatorio, Long> {


}
