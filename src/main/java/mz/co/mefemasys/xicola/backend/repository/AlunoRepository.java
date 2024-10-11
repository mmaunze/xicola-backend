package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Aluno;
import mz.co.mefemasys.xicola.backend.models.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {


    @Query("SELECT count(a) FROM Aluno a WHERE a.estado.descricao = :descricao")
    Long findAlunosByEstado (@Param("descricao") String descricao);
}
