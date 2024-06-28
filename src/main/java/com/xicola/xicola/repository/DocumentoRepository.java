package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}
