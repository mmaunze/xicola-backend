package com.xicola.xicola.repository;

import com.xicola.xicola.model.ItemOrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdemCompraRepository extends JpaRepository<ItemOrdemCompra, Integer> {


}
