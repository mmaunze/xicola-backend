package com.xicola.xicola.repository;

import com.xicola.xicola.model.DetalheRelatorioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalheRelatorioFinanceiroRepository extends JpaRepository<DetalheRelatorioFinanceiro, Integer> {

}
