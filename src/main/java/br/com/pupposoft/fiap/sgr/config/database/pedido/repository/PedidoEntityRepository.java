package br.com.pupposoft.fiap.sgr.config.database.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;

@Repository
interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long>{

}
