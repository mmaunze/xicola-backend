package com.xicola.xicola.repository;

import com.xicola.xicola.model.TipoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionario, Long> {

}
