package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ItemDto {
	  private Long id;
	  private Long quantidade;
	  private ProdutoDto produto;
	  private PedidoDto pedido;
	  private Double valorUnitario;
}
