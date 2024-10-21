package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoTransacao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade TipoTransacao.
 * <p>
 * Este repositório estende JpaRepository, fornecendo métodos padrão para
 * operações CRUD e mais. Também permite o uso de consultas nomeadas definidas
 * na entidade TipoTransacao.
 */
@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {

}
