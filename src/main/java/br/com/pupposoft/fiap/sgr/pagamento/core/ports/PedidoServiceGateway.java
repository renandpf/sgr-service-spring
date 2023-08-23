package br.com.pupposoft.fiap.sgr.pagamento.core.ports;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;

public interface PedidoServiceGateway {
	Optional<PedidoDto> obterPorId(Long pedidoId);
    void alterarStatus(PedidoDto pedido);
}
