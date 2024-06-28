package com.xicola.xicola.repository;

import com.xicola.xicola.model.AreaCientifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCientificaRepository extends JpaRepository<AreaCientifica, Integer> {

}
