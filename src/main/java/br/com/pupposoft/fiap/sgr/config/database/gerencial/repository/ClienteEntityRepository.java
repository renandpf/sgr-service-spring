package br.com.pupposoft.fiap.sgr.config.database.gerencial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;

@Repository
public interface ClienteEntityRepository extends JpaRepository<ClienteEntity, Long> {

}
