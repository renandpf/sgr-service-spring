package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.repository;

import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public class PedidoMySqlRepositoryGateway implements PedidoRepositoryGateway {

	@Override
	public Long criar(PedidoDto pedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatus(PedidoDto pedido) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PedidoDto> obterEmAndamento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PedidoDto> obterPorStatusAndIdentificadorPagamento(Status status, String identificadorPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PedidoDto> obterPorIdentificadorPagamento(String identificadorPagamento) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
