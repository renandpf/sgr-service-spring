package br.com.pupposoft.fiap.sgr.pedido.core.usecase;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public interface ObterPedidoUseCase {
	PedidoDto obterPorId(Long id);
    List<PedidoDto> obterEmAndamento();
    PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento);
}
