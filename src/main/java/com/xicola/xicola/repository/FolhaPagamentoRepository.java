package com.xicola.xicola.repository;

import com.xicola.xicola.model.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {

}
