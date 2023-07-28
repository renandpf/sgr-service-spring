package br.com.pupposoft.fiap.sgr.config.database.pagamento.entity;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
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

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pagamento")
public class PagamentoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String identificadorPagamentoExterno;
	
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "pedidoId")
	private PedidoEntity pedido;

}
