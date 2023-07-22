package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PagamentoDto {
	private Long id;
	private PedidoDto pedido;
	private String identificadorPagamento;
}
