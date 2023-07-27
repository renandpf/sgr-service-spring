package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtualizarStatusPedidoUseCaseImpl implements AtualizarStatusPedidoUseCase {

	@Autowired
	private PedidoRepositoryGateway pedidoRepositoryGateway;
	
	@Autowired
	private ObterPedidoUseCase obterPedidoUseCase;
	
	@Override
	public void atualizarStatus(Long pedidoId, Status status) {
        log.trace("Start id={}, status={}", pedidoId, status);
        
        PedidoDto pedidoDto = obterPedidoUseCase.obterPorId(pedidoId);
        
        Pedido pedido = Pedido.builder().id(pedidoId).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(status);
        
        this.pedidoRepositoryGateway.atualizarStatus(PedidoDto.builder().id(pedidoId).statusId(pedidoId).build());
        log.trace("End");
	}
	
}
