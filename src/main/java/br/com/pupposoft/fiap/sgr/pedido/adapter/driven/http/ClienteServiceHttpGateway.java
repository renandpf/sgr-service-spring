package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.http;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ClienteServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;

public class ClienteServiceHttpGateway implements ClienteServiceGateway {

	@Override
	public Optional<ClienteDto> obterPorId(Long clienteId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
