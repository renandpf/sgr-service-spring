package br.com.pupposoft.fiap.sgr.config.database.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;

@Repository
public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long>{

	List<PedidoEntity> findByStatusIdIn(List<Long> statusIdList);

}
