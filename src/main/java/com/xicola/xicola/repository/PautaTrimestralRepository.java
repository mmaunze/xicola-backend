package com.xicola.xicola.repository;

import com.xicola.xicola.model.PautaTrimestral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PautaTrimestralRepository extends JpaRepository<PautaTrimestral, Integer> {


}
