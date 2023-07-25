package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private BigDecimal valor;

	@ManyToOne()
	private ProdutoEntity produto;

	@ManyToOne
	private PedidoEntity pedido;
	

}
