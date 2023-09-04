package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.CamposObrigatoriosNaoPreechidoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ClienteNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.ClienteGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Item;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class EfetuarPagamentoUseCaseImpl implements EfetuarPagamentoUseCase {

	private PedidoGateway pedidoGateway;
	
	private PlataformaPagamentoFactory plataformaPagamentoFactory;
	
	private PagamentoGateway pagamentoGateway;
	
	private ClienteGateway clienteGateway;
	
	@Override
	public EfetuarPagamentoReturnDto efetuar(EfetuarPagamentoParamDto paramsDto) {
        log.trace("Start dto={}", paramsDto);

        validaCamposObrigatorios(paramsDto.getPagamento());

        PedidoDto pedidoDto = obtemPedidoVerificandoSeEleExiste(paramsDto.getPagamento().getPedido().getId());

        ClienteDto clienteDto = obtemClienteVerificandoSeEleExiste(pedidoDto.getClienteId());
        
        setStatusDoPedido(pedidoDto);
        
        calcularAtribuirValorPagamento(paramsDto, pedidoDto);
        
        String pagamentoExternoId = enviaPagamentoSistemaExterno(paramsDto, pedidoDto.getValor(), clienteDto);
        
        //TODO: deve ocorrer rollback em caso de falha no passo de alterarStatus do serviço
        Long idPagamento = this.pagamentoGateway.criar(paramsDto.getPagamento());

        pedidoGateway.alterarStatus(pedidoDto);

        EfetuarPagamentoReturnDto returnDto = EfetuarPagamentoReturnDto.builder().pagamentoId(idPagamento).pagamentoExternoId(pagamentoExternoId).build();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

	private void calcularAtribuirValorPagamento(EfetuarPagamentoParamDto paramsDto, PedidoDto pedidoDto) {
		List<Item> itens = pedidoDto.getItens().stream().map(i -> Item.builder()
        		.id(i.getId())
        		.valorUnitario(new BigDecimal(i.getValorUnitario()))
        		.quantidade(i.getQuantidade())
        		.build()).toList();
        
        Pedido pedido = Pedido.builder()
        		.id(pedidoDto.getId())
        		.itens(itens)
        		.build();
        
        BigDecimal valorTotal = pedido.getValorTotal();
        
        pedidoDto.setValor(valorTotal.doubleValue());
        
        paramsDto.getPagamento().setValor(valorTotal.doubleValue());
	}

	//TODO: Método candidato a ser usecase 
	private String enviaPagamentoSistemaExterno(EfetuarPagamentoParamDto dto, Double valor, ClienteDto clienteDto) {
		
		EnviaPagamentoExternoParamDto enviaPagamentoExternoParamDto = 
				EnviaPagamentoExternoParamDto.builder()
				.nomeProduto("Combo de lanches")
				.nomeCliente(clienteDto.getNome())
				.sobrenomeCliente("")
				.emailCliente(clienteDto.getEmail())
				.parcelas(1)
				.valor(valor)
				.modoPagamento(ModoPagamento.valueOf(dto.getPagamento().getFormaPagamento()))
				.build();
		
		EnviaPagamentoReturnDto responsePagamentoDto = plataformaPagamentoFactory.obter().enviarPagamento(enviaPagamentoExternoParamDto);
		
        dto.getPagamento().setPagamentoExternoId(responsePagamentoDto.getPagamentoExternoId());
        
        return responsePagamentoDto.getPagamentoExternoId();
	}


	private void setStatusDoPedido(PedidoDto pedidoDto) {
		final Status statusAguardandoConfirmacaoPagamento = Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO;
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(statusAguardandoConfirmacaoPagamento);//Regras dentro do domain
        pedidoDto.setStatusId(Status.get(statusAguardandoConfirmacaoPagamento));
	}

	
	//TODO: Método candidato a ser usecase 
	private PedidoDto obtemPedidoVerificandoSeEleExiste(Long pedidoId) {
		Optional<PedidoDto> pedidoOp = pedidoGateway.obterPorId(pedidoId);
		if (pedidoOp.isEmpty()) {
			log.warn("Pedido não encontrado. pedidoId={}", pedidoId);
			throw new PedidoNaoEncontradoException();
		}

		return pedidoOp.get();
	}

	//TODO: Método candidato a ser usecase 
	private ClienteDto obtemClienteVerificandoSeEleExiste(Long clienteId) {
		Optional<ClienteDto> clienteOp = clienteGateway.obterPorId(clienteId);
		if (clienteOp.isEmpty()) {
			log.warn("Cliente não encontrado. clienteId={}", clienteId);
			throw new ClienteNaoEncontradoException();
		}

		return clienteOp.get();
	}

	
	private void validaCamposObrigatorios(PagamentoDto pagamentoDto) {
		final List<String>  mensagens = new ArrayList<>();
		if (pagamentoDto.getPedido() == null) {
			mensagens.add("Identificador do pedido (pedido id)");
		}

		if (pagamentoDto.getFormaPagamento() == null) {
			mensagens.add("Meio de pagamento não informado");
		}
		
		if (!mensagens.isEmpty()) {
			throw new CamposObrigatoriosNaoPreechidoException(mensagens.stream().reduce("", (a,b) -> a + ", " + b ));
		}
	}
}
