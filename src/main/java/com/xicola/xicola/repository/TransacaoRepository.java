package com.xicola.xicola.repository;

import com.xicola.xicola.model.Transacao;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Transacao.
 *
 * Este repositório estende JpaRepository, fornecendo métodos padrão para
 * operações CRUD e mais. Também permite o uso de consultas nomeadas definidas
 * na entidade Transacao e consultas personalizadas.
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    @Query("SELECT t FROM Transacao t WHERE t.valor >= :minValue AND t.valor <= :maxValue")
    List<Transacao> findByValorRange(BigDecimal minValue, BigDecimal maxValue);


}
