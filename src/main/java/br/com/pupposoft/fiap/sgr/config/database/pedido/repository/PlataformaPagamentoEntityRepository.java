package br.com.pupposoft.fiap.sgr.config.database.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PlataformaPagamentoEntity;

@Repository
public interface PlataformaPagamentoEntityRepository extends JpaRepository<PlataformaPagamentoEntity, Long>{

	List<PlataformaPagamentoEntity> findByStatus(Long statusId);

}
