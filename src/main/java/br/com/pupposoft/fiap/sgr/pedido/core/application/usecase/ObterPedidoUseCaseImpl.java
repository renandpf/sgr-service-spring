package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PagamentoServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.PagamentoNotFoundException;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.PedidoNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObterPedidoUseCaseImpl implements ObterPedidoUseCase {

	private PagamentoServiceGateway pagamentoServiceGateway;
	
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
        List<PedidoDto> pedidos = this.pedidoRepositoryGateway.obterPorStatus(Arrays.asList(
        		Status.PAGO, 
        		Status.EM_PREPARACAO));
        log.trace("End pedidos={}", pedidos);
        return pedidos;
	}

	@Override
	public PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
        log.trace("Start identificadorPagamento={}", identificadorPagamento);
        Optional<PagamentoDto> pagamentoOp = this.pagamentoServiceGateway.obterPorIdentificadorPagamento(identificadorPagamento);

        if (pagamentoOp.isEmpty()) {
            log.warn("Pagamento não encontrado com identificadorPagamento: {}", identificadorPagamento);
            throw new PagamentoNotFoundException();
        }
        
        PagamentoDto pagamentoDto = pagamentoOp.get();
        PedidoDto pedidoDto = obterPorId(pagamentoDto.getPedido().getId());

        log.trace("End pedidoDto={}", pedidoDto);
        return pedidoDto;
	}

}
