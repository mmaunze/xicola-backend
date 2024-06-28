package com.xicola.xicola.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.TipoTransacao;

/**
 * Repositório JPA para a entidade TipoTransacao.
 *
 * Este repositório estende JpaRepository, fornecendo métodos padrão para
 * operações CRUD e mais. Também permite o uso de consultas nomeadas definidas
 * na entidade TipoTransacao.
 */
@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {


}
