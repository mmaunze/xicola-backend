package com.xicola.xicola.repository;

import com.xicola.xicola.model.CategoriaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaFinanceiraRepository extends JpaRepository<CategoriaFinanceira, Integer> {

}
