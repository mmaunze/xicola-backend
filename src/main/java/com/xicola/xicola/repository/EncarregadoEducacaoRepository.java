package com.xicola.xicola.repository;

import com.xicola.xicola.model.EncarregadoEducacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncarregadoEducacaoRepository extends JpaRepository<EncarregadoEducacao, Long> {

}
