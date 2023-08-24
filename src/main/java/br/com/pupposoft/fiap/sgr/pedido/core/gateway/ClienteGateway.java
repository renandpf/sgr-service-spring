package br.com.pupposoft.fiap.sgr.pedido.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;

public interface ClienteGateway {
	Optional<ClienteDto> obterPorId(Long clienteId);
}
