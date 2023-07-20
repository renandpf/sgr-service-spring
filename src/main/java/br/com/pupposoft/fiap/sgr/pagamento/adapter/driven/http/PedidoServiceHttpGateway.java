package br.com.pupposoft.fiap.sgr.pagamento.adapter.driven.http;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PedidoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;

@Component
public class PedidoServiceHttpGateway implements PedidoServiceGateway {

	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void alterarStatus(PedidoDto pedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PedidoDto> obterPorIdentificadorPagamento(String identificadorPagamento) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
