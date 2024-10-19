package mz.co.mefemasys.xicola.backend.repository;

import java.util.Optional;
import mz.co.mefemasys.xicola.backend.models.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    @Query("SELECT tp FROM TipoPessoa tp WHERE tp.descricao = :descricao")
    Optional<TipoPessoa> findTipoPessoa(String descricao);


}
