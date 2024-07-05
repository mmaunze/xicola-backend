package com.xicola.xicola.repository;

import com.xicola.xicola.model.TipoAvaliacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAvaliacaoRepository extends JpaRepository<TipoAvaliacao, Integer> {

    @Query("SELECT ta FROM TipoAvaliacao ta WHERE ta.descricao = :tipo")
    Optional<TipoAvaliacao> findTipoAvaliacao(String tipo);

}
