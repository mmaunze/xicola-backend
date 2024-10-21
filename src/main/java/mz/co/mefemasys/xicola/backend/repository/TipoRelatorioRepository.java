package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.TipoRelatorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TipoRelatorioRepository extends JpaRepository<TipoRelatorio, Long> {

}
