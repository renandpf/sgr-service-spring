package br.com.pupposoft.fiap.sgr.pedido.core.usecase;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public interface AtualizarStatusPedidoUseCase {
	void atualizarStatus(Long pedidoId, Status status);
}
