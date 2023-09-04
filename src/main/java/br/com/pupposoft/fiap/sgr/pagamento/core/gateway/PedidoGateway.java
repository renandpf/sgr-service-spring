package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;

public interface PedidoGateway {
	Optional<PedidoDto> obterPorId(Long pedidoId);
    void alterarStatus(PedidoDto pedido);
}
