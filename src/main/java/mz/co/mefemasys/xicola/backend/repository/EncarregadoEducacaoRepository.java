package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.EncarregadoEducacao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface EncarregadoEducacaoRepository extends JpaRepository<EncarregadoEducacao, Long> {

    @Query("SELECT count(ed) FROM EncarregadoEducacao ed WHERE ed.estado.descricao = :descricao")
    Long totalEstado(@Param("descricao") String descricao);

}
