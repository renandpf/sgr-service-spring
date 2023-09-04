package br.com.pupposoft.fiap.sgr.pedido.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;

public interface ProdutoGateway {
	Optional<ProdutoDto> obterPorId(Long produtoId);
}
