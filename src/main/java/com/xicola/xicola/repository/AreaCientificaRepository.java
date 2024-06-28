package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.AreaCientifica;

@Repository
public interface AreaCientificaRepository extends JpaRepository<AreaCientifica, Integer> {

}
