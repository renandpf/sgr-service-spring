package br.com.pupposoft.fiap.sgr.config.database.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.ItemEntity;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long>{

}
