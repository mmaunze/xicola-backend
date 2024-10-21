package mz.co.mefemasys.xicola.backend.repository;

import mz.co.mefemasys.xicola.backend.models.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
