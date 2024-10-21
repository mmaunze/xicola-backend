package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoAvaliacaoRepository extends JpaRepository<TipoAvaliacao, Long> {

    @Query("SELECT ta FROM TipoAvaliacao ta WHERE ta.descricao = :tipo")
    Optional<TipoAvaliacao> findTipoAvaliacao(String tipo);

}
