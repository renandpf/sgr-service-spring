package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoExternoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PedidoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfirmarPagamentoUseCaseImpl implements ConfirmarPagamentoUseCase {
	
	private PedidoServiceGateway pedidoServiceGateway;
	
	private PagamentoExternoServiceGateway pagamentoExternoServiceGateway;

	private PagamentoRepositoryGateway pagamentoRepositoryGateway;
	
	@Override
    public void confirmar(String identificadorPagamento, String statusPagamento) {
		//TODO - alterar este método para NÃO receber o status. Na pratica o sistema deve ir buscar o status no sistema terceiro (e não receber)
        log.trace("Start identificadorPagamento={}, statusPagamento={}", identificadorPagamento, statusPagamento);

        PagamentoDto pagamentoDto = this.obtemPedidoPorIdentificadorPagamento(identificadorPagamento);
        PedidoDto pedidoDto = getPedidoById(pagamentoDto.getPedido().getId());
        Status status = this.pagamentoExternoServiceGateway.mapStatus(statusPagamento);
        
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(status).build();
        pedido.setStatus(status);//Regras de negócio dentro do domain

        PedidoDto pedidoDtoStatusPago = PedidoDto.builder()
        		.id(pedido.getId())
        		.statusId(Status.get(status))
        		.build();

        this.pedidoServiceGateway.alterarStatus(pedidoDtoStatusPago);

        log.trace("End");
    }


	private PedidoDto getPedidoById(Long pedidoId) {
        Optional<PedidoDto> pedidoDtoOp = pedidoServiceGateway.obterPorId(pedidoId);
        if(pedidoDtoOp.isEmpty()) {
        	log.warn("Pedido não encontrado. pedidoId={}", pedidoId);
        	throw new PedidoNaoEncontradoException();
        }
        
        return pedidoDtoOp.get();
	}


    private PagamentoDto obtemPedidoPorIdentificadorPagamento(String identificadorPagamento) {
        Optional<PagamentoDto> pagamentoDtoOp = this.pagamentoRepositoryGateway.obterPorIdentificadorPagamento(identificadorPagamento);
        if (pagamentoDtoOp.isEmpty()) {
            log.warn("Pagamento não encontrado. identificadorPagamento={}", identificadorPagamento);
            throw new PagamentoNaoEncontradoException();
        }

        return pagamentoDtoOp.get();
    }
	
}
