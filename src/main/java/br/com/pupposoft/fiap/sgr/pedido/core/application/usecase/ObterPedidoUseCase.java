package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public interface ObterPedidoUseCase {
	PedidoDto obterPorId(Long id);
    List<PedidoDto> obterEmAndamento();
    List<PedidoDto> obterPorStatusAndIdentificadorPagamento(Status status, String identificadorPagamento);
    PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento);
}
