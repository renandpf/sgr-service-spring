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
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ValorPagamentoInvalidoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.ClienteGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
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

        BigDecimal valorEsperado = new BigDecimal(pedidoDto.getValor());
        BigDecimal valorRecebido = paramsDto.getPagamento().getValor();
        if(!valorRecebido.equals(valorEsperado)) {
        	log.warn("Valor do pagamento diferente do pedido");
        	throw new ValorPagamentoInvalidoException();
        }
        
        ClienteDto clienteDto = obtemClienteVerificandoSeEleExiste(pedidoDto.getClienteId());
        
        setStatusDoPedido(pedidoDto);
        
        
        enviaPagamentoSistemaExterno(paramsDto, pedidoDto, clienteDto);
        
        //TODO: deve ocorrer rollback em caso de falha no passo de alterarStatus do serviço
        Long idPagamento = this.pagamentoGateway.criar(paramsDto.getPagamento());

        pedidoGateway.alterarStatus(pedidoDto);

        EfetuarPagamentoReturnDto returnDto = EfetuarPagamentoReturnDto.builder().pagamentoId(idPagamento).build();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

	private void enviaPagamentoSistemaExterno(EfetuarPagamentoParamDto dto, PedidoDto pedidoDto, ClienteDto clienteDto) {
		
		EnviaPagamentoExternoParamDto enviaPagamentoExternoParamDto = 
				EnviaPagamentoExternoParamDto.builder()
				.nomeProduto("Combo de lanches")
				.nomeCliente(clienteDto.getNome())
				.sobrenomeCliente("")
				.emailCliente(clienteDto.getEmail())
				.parcelas(1)
				.valor(dto.getPagamento().getValor().doubleValue())
				.modoPagamento(ModoPagamento.valueOf(dto.getPagamento().getFormaPagamento()))
				.build();
		
		EnviaPagamentoReturnDto responsePagamentoDto = plataformaPagamentoFactory.obter().enviarPagamento(enviaPagamentoExternoParamDto);
		
        dto.getPagamento().setIdentificadorPagamentoExterno(responsePagamentoDto.getIdentificadorPagamento());
        dto.getPagamento().setPedido(pedidoDto);
	}


	private void setStatusDoPedido(PedidoDto pedidoDto) {
		final Status statusAguardandoConfirmacaoPagamento = Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO;
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(statusAguardandoConfirmacaoPagamento);
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
