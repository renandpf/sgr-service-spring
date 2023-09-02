package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AtualizarPedidoUseCaseImpl implements AtualizarStatusPagamentoUseCase {
	
	private PedidoGateway pedidoGateway;
	
	private PlataformaPagamentoFactory plataformaPagamentoFactory;
	
	private PagamentoGateway pagamentoGateway;
	
	@Override
    public void atualizar(PlataformaPagamento plataformaPagamento, String identificadorPagamento) {
		log.trace("Start plataformaPagamento={}, identificadorPagamento={}", plataformaPagamento, identificadorPagamento);
		
        PagamentoDto pagamentoDto = obtemPedidoPorIdentificadorPagamento(identificadorPagamento);
        PedidoDto pedidoDto = getPedidoById(pagamentoDto.getPedido().getId());
        
        Status newStatus = plataformaPagamentoFactory.obter(plataformaPagamento).obtemStatus(identificadorPagamento);
        
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(newStatus);//Aplica as regras de negócio que estão dentro do domain

        PedidoDto pedidoDtoStatusPago = PedidoDto.builder()
        		.id(pedido.getId())
        		.statusId(Status.get(newStatus))
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
