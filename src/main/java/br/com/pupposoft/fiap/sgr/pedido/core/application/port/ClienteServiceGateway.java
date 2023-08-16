package br.com.pupposoft.fiap.sgr.pedido.core.application.port;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;

public interface ClienteServiceGateway {
	Optional<ClienteDto> obterPorId(Long clienteId);
}
