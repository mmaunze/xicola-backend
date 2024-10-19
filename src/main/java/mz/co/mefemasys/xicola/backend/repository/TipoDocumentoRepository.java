package mz.co.mefemasys.xicola.backend.repository;

import java.util.Optional;
import mz.co.mefemasys.xicola.backend.models.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

    @Query("SELECT TipoDocumento FROM Estado td WHERE td.descricao = :tipoDocumento")
    Optional<TipoDocumento> findTipoDocumento(String tipoDocumento);

}
