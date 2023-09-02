package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Produto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Item {
	private Long id;
	private Pedido pedido;
	@Setter
	private Produto produto;
	private Long quantidade;
	private BigDecimal valorUnitario;
	
	public BigDecimal getValorTotal() {
		return new BigDecimal(quantidade).multiply(valorUnitario);
	}
}
