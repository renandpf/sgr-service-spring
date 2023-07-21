package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.PedidoNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObterPedidoUseCaseImpl implements ObterPedidoUseCase {

	private PedidoRepositoryGateway pedidoRepositoryGateway;
	
	@Override
	public PedidoDto obterPorId(Long id) {
        log.trace("Start id={}", id);

        Optional<PedidoDto> pedidoOp = this.pedidoRepositoryGateway.obterPorId(id);
        if (pedidoOp.isEmpty()) {
            log.warn("Pedido não encontrado. id={}", id);
            throw new PedidoNotFoundException();
        }

        PedidoDto pedidoDto = pedidoOp.get();
        log.trace("End pedidoDto={}", pedidoDto);
        return pedidoDto;
	}

	@Override
	public List<PedidoDto> obterEmAndamento() {
        log.trace("Start");
        List<PedidoDto> pedidos = this.pedidoRepositoryGateway.obterEmAndamento();
        log.trace("End pedidos={}", pedidos);
        return pedidos;
	}

	@Override
	public List<PedidoDto> obterPorStatusAndIdentificadorPagamento(Status status, String identificadorPagamento) {
        log.trace("Start status={}, identificadorPagamento={}", status, identificadorPagamento);
        List<PedidoDto> pedidos = this.pedidoRepositoryGateway.obterPorStatusAndIdentificadorPagamento(status, identificadorPagamento);
        log.trace("End pedidos={}", pedidos);
        return pedidos;
	}

	@Override
	public PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
        log.trace("Start identificadorPagamento={}", identificadorPagamento);
        Optional<PedidoDto> pedidoOp = this.pedidoRepositoryGateway.obterPorIdentificadorPagamento(identificadorPagamento);

        if (pedidoOp.isEmpty()) {
            log.warn("Pedido não encontrado. identificadorPagamento={}", identificadorPagamento);
            throw new PedidoNotFoundException();
        }

        PedidoDto pedidoDto = pedidoOp.get();
        log.trace("End pedidoDto={}", pedidoDto);
        return pedidoDto;
	}

}
