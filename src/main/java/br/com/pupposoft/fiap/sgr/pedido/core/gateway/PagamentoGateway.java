package br.com.pupposoft.fiap.sgr.pedido.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.PagamentoDto;

public interface PagamentoGateway {
	Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento);
}
