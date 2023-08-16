package br.com.pupposoft.fiap.sgr.pedido.core.application.port;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;

public interface ProdutoServiceGateway {
	Optional<ProdutoDto> obterPorId(Long produtoId);
}
