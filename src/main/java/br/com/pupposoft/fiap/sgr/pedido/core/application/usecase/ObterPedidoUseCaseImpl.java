package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public class ObterPedidoUseCaseImpl implements ObterPedidoUseCase {

	@Override
	public PedidoDto obterPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
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
	public PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

}
