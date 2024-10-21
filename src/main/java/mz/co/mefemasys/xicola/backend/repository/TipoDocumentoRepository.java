package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

    @Query("SELECT TipoDocumento FROM Estado td WHERE td.descricao = :tipoDocumento")
    Optional<TipoDocumento> findTipoDocumento(String tipoDocumento);

}
