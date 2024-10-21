package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoFuncionario;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionario, Long> {

    @Query("SELECT tf FROM TipoFuncionario tf WHERE tf.descricao = :descricao")
    Optional<TipoFuncionario> findTipoFuncionario(@Param("descricao") String descricao);

}
