package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Produto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Item {
	private Long id;
	private Pedido pedido;
	private Produto produto;
	private Long quantidade;
	private BigDecimal valorUnitario;
}
