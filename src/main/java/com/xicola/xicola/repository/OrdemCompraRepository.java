package com.xicola.xicola.repository;

import com.xicola.xicola.model.OrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemCompraRepository extends JpaRepository<OrdemCompra, Integer> {

}
