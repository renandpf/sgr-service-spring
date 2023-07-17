package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoExternoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PedidoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.CamposObrigatoriosNaoPreechidoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PedidoNotFoundException;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EfetuarPagamentoUseCaseImpl implements EfetuarPagamentoUseCase {

	private PedidoServiceGateway pedidoServiceGateway;
	private PagamentoExternoServiceGateway pagamentoExternoServiceGateway;
	private PagamentoRepositoryGateway pagamentoRepositoryGateway;

	@Override
	public EfetuarPagamentoReturnDto efetuar(EfetuarPagamentoParamDto dto) {
        log.trace("Start dto={}", dto);

        this.validaCamposObrigatorios(dto.getPagamento());

        PedidoDto pedidoDto = this.obtemPedidoVerificandoSeEleExiste(dto.getPagamento());
        
        Pedido pedido = Pedido.builder().id(pedidoDto.getId()).status(Status.get(pedidoDto.getStatusId())).build();
        pedido.setStatus(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO);

        EnviaPagamentoReturnDto responsePagamentoDto = 
        		this.pagamentoExternoServiceGateway.enviarPagamento(
        				EnviaPagamentoExternoParamDto.builder()
        					.cartoesCredito(dto.getPagamento()
        					.getCartoesCredito())
        				.build());
        dto.getPagamento().setIdentificadorPagamentoExterno(responsePagamentoDto.getIdentificadorPagamento());
        dto.getPagamento().setPedido(pedidoDto);

        //TODO: deve ocorrer rollback em caso de falha no passo de alterarStatus do serviço
        Long idPagamento = this.pagamentoRepositoryGateway.criar(dto.getPagamento());

        this.pedidoServiceGateway.alterarStatus(pedidoDto);

        EfetuarPagamentoReturnDto returnDto = EfetuarPagamentoReturnDto.builder().pagamentoId(idPagamento).build();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

	private PedidoDto obtemPedidoVerificandoSeEleExiste(PagamentoDto pagamento) {
		Optional<PedidoDto> pedidoOp = this.pedidoServiceGateway.obterPorId(pagamento.getPedido().getId());
		if (pedidoOp.isEmpty()) {
			log.warn("Pedido não encontrado. pagamento.pedido.id={}", pagamento.getPedido().getId());
			throw new PedidoNotFoundException();
		}

		return pedidoOp.get();
	}

	private void validaCamposObrigatorios(PagamentoDto pagamentoDto) {
		final List<String>  mensagens = new ArrayList<>();
		if (pagamentoDto.getPedido() == null) {
			mensagens.add("Identificador do pedido (pedido id)");
		}

		if (!pagamentoDto.haveCartaoCredito() ) {
			mensagens.add("Meio de pagamento não informado");
		}

		if (!mensagens.isEmpty()) {
			throw new CamposObrigatoriosNaoPreechidoException(mensagens.stream().reduce("", (a,b) -> a + ", " + b ));
		}
	}


}
