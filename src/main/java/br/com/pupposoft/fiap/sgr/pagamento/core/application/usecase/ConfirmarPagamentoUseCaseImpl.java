package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoExternoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PedidoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNotFoundException;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfirmarPagamentoUseCaseImpl implements ConfirmarPagamentoUseCase {
	
	private PedidoServiceGateway pedidoServiceGateway;
	
	private PagamentoExternoServiceGateway pagamentoExternoServiceGateway;

    public void confirmar(String identificadorPagamento, String statusPagamento) {
        log.trace("Start identificadorPagamento={}, statusPagamento={}", identificadorPagamento, statusPagamento);

        PedidoDto pedidoDto = this.obtemPedidoPorPagamentoId(identificadorPagamento);
        
        Status status = this.pagamentoExternoServiceGateway.mapStatus(statusPagamento);
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(status);

        PedidoDto pedidoDtoStatusPago = PedidoDto.builder()
        		.id(pedido.getId())
        		.statusId(Status.get(status))
        		.build();

        this.pedidoServiceGateway.alterarStatus(pedidoDtoStatusPago);

        log.trace("End");
    }


    private PedidoDto obtemPedidoPorPagamentoId(String identificadorPagamento) {
        Optional<PedidoDto> pedidoDtoOp = this.pedidoServiceGateway.obterPorIdentificadorPagamento(identificadorPagamento);
        if (pedidoDtoOp.isEmpty()) {
            log.warn("Pedido não encontrado. identificadorPagamento={}", identificadorPagamento);
            throw new PedidoNotFoundException();
        }

        return pedidoDtoOp.get();
    }
	
}
