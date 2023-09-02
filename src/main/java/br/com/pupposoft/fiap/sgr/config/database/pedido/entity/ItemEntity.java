package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Item")
public class ItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long quantidade;
	private Double valorUnitario;

	@ManyToOne()
	@JoinColumn(name = "produtoId")
	private ProdutoEntity produto;

	@Setter
	@ManyToOne
	@JoinColumn(name = "pedidoId")
	private PedidoEntity pedido;
	

}
