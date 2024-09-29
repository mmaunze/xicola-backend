package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Disciplina;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    @Query("SELECT d FROM Disciplina d WHERE d.nomeDisciplina = :nomeDisciplina")
    Optional<Disciplina> findDisciplina(@Param("nomeDisciplina") String nomeDisciplina);

}
