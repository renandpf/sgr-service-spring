package br.com.pupposoft.fiap.sgr.config.database.pagamento.entity;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
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
@Table(name = "Pagamento")
public class PagamentoEntity {
	@Id
	private Long id;

	private String identificadorPagamentoExterno;

	@ManyToOne
	private PedidoEntity pedido;

}
