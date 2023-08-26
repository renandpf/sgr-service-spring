package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ConfirmarPagamentoUseCaseImpl implements ConfirmarPagamentoUseCase {
	
	private PedidoGateway pedidoGateway;
	
	private PlataformaPagamentoGateway pagamentoExternoGateway;

	private PagamentoGateway pagamentoGateway;
	
	@Override
    public void confirmar(String identificadorPagamento, String statusPagamento) {
		//TODO - alterar este método para NÃO receber o status. Na pratica o sistema deve ir buscar o status no sistema terceiro (e não receber)
        log.trace("Start identificadorPagamento={}, statusPagamento={}", identificadorPagamento, statusPagamento);

        PagamentoDto pagamentoDto = this.obtemPedidoPorIdentificadorPagamento(identificadorPagamento);
        PedidoDto pedidoDto = getPedidoById(pagamentoDto.getPedido().getId());
        Status status = this.pagamentoExternoGateway.mapStatus(statusPagamento);
        
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(status).build();
        pedido.setStatus(status);//Regras de negócio dentro do domain

        PedidoDto pedidoDtoStatusPago = PedidoDto.builder()
        		.id(pedido.getId())
        		.statusId(Status.get(status))
        		.build();

        this.pedidoGateway.alterarStatus(pedidoDtoStatusPago);

        log.trace("End");
    }


	private PedidoDto getPedidoById(Long pedidoId) {
        Optional<PedidoDto> pedidoDtoOp = pedidoGateway.obterPorId(pedidoId);
        if(pedidoDtoOp.isEmpty()) {
        	log.warn("Pedido não encontrado. pedidoId={}", pedidoId);
        	throw new PedidoNaoEncontradoException();
        }
        
        return pedidoDtoOp.get();
	}


    private PagamentoDto obtemPedidoPorIdentificadorPagamento(String identificadorPagamento) {
        Optional<PagamentoDto> pagamentoDtoOp = this.pagamentoGateway.obterPorIdentificadorPagamento(identificadorPagamento);
        if (pagamentoDtoOp.isEmpty()) {
            log.warn("Pagamento não encontrado. identificadorPagamento={}", identificadorPagamento);
            throw new PagamentoNaoEncontradoException();
        }

        return pagamentoDtoOp.get();
    }
	
}
