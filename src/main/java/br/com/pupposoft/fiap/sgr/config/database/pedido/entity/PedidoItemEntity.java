package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import jakarta.persistence.Entity;
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
@Table(name = "PedidoItem")
public class PedidoItemEntity {
	  private Long id;

	  private Long quantidade;

	  private BigDecimal valorUnitario;

	  private BigDecimal valorTotal;

	  //@ManyToOne(() => ProdutoEntity, { nullable: false })
	  private ProdutoEntity produto;

	  //@ManyToOne(() => PedidoEntity, (pedido) => pedido.itens, { nullable: false })
	  private PedidoEntity pedido;

}
