package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoPessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    @Query("SELECT tp FROM TipoPessoa tp WHERE tp.descricao = :descricao")
    Optional<TipoPessoa> findTipoPessoa(String descricao);

}
