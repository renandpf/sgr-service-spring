package br.com.pupposoft.fiap.sgr.pedido.core.port;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.PagamentoDto;

public interface PagamentoServiceGateway {
	Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento);
}
