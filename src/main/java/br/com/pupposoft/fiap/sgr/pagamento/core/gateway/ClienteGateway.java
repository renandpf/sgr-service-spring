package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.ClienteDto;

public interface ClienteGateway {

	Optional<ClienteDto> obterPorId(Long clienteId);

}
