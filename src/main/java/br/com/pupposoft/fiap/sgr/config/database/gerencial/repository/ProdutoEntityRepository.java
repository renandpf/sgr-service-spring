package br.com.pupposoft.fiap.sgr.config.database.gerencial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;

@Repository
public interface ProdutoEntityRepository extends JpaRepository<ProdutoEntity, Long> {

}
