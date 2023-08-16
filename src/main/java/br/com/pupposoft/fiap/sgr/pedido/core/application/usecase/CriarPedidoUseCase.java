package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public interface CriarPedidoUseCase {
	Long criar(PedidoDto pedido);
}
