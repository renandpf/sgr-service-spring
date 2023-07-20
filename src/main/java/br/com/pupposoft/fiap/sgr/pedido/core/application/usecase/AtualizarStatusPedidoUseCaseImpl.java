package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.PedidoNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtualizarStatusPedidoUseCaseImpl implements AtualizarStatusPedidoUseCase {

	@Autowired
	private PedidoRepositoryGateway pedidoRepositoryGateway;
	
	@Override
	public void atualizarStatus(Long pedidoId, Status status) {
        log.trace("Start id={}, status={}", pedidoId, status);
        Optional<PedidoDto> pedidoOp = this.pedidoRepositoryGateway.obterPorId(pedidoId);
        if (pedidoOp.isEmpty()) {
            log.warn("Pedido id={} não encontrado", pedidoId);
            throw new PedidoNotFoundException();
        }
        PedidoDto pedidoDto = pedidoOp.get();
        Pedido pedido = Pedido.builder().id(pedidoId).status(status).build();
        pedido.setStatus(status);
        
        this.pedidoRepositoryGateway.atualizarStatus(pedidoDto);
        log.trace("End");
	}
	
}
