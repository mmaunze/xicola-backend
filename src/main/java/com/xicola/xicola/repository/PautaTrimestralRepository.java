package com.xicola.xicola.repository;

import com.xicola.xicola.model.PautaTrimestral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaTrimestralRepository extends JpaRepository<PautaTrimestral, Integer> {


}
