package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Item")
public class ItemEntity {
	@Id
	private Long id;
	private Long quantidade;

	//@ManyToOne(() => ProdutoEntity, { nullable: false })
	private ProdutoEntity produto;

	//@ManyToOne(() => PedidoEntity, (pedido) => pedido.itens, { nullable: false })
	private PedidoEntity pedido;
	
	public ItemEntity(ItemDto item, PedidoEntity pedido, ProdutoEntity produto) {
		id = item.getId();
		quantidade = item.getQuantidade();
		produto = ProdutoEntity.builder().id(item.getProduto().getId()).build();
		
		this.pedido = pedido;
		this.produto = produto;
	}

}
