package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public interface AtualizarStatusPedidoUseCase {
	void atualizarStatus(Long pedidoId, Status status);
}
