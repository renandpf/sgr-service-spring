package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ItemDto {
	private Long quantidade;
	private PedidoDto pedido;
	private ProdutoDto produto;
}
