package br.com.pupposoft.fiap.sgr.config.database.gerencial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;

@Repository
public interface ClienteEntityRepository extends JpaRepository<ClienteEntity, Long> {

	Optional<ClienteEntity> findByCpf(String cpf);

	Optional<ClienteEntity> findByEmail(String email);

}
