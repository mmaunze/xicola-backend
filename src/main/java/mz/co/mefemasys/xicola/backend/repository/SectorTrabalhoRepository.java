package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.SectorTrabalho;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorTrabalhoRepository extends JpaRepository<SectorTrabalho, Long> {

    @Query("SELECT st FROM SectorTrabalho st WHERE st.descricao = :descricao")
    Optional<SectorTrabalho> findSector(@Param("descricao") String descricao);

}
