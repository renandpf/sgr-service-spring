package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.http;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ProdutoServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;

public class ProdutoServiceHttpGateway implements ProdutoServiceGateway {

	@Override
	public Optional<ProdutoDto> obterPorId(Long produtoId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
