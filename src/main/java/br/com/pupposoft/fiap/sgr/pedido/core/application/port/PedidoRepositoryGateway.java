package br.com.pupposoft.fiap.sgr.pedido.core.application.port;

import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;

public interface PedidoRepositoryGateway {
    Long criar(PedidoDto pedido);
    void atualizarStatus(PedidoDto pedido);
    Optional<PedidoDto> obterPorId(Long pedidoId);
    List<PedidoDto> obterPorStatus(List<Status> statusList);
}
