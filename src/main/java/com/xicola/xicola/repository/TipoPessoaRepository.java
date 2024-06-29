package com.xicola.xicola.repository;

import com.xicola.xicola.model.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Integer> {


}
