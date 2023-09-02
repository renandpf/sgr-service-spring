package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomLong;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.ItemDto;
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
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;

@ExtendWith(MockitoExtension.class)
class EfetuarPagamentoUseCaseUnitTest {
	
	@Mock
	private PedidoGateway pedidoGateway;
	
	@Mock
	private PlataformaPagamentoFactory plataformaPagamentoFactory;
	
	@Mock
	private PagamentoGateway pagamentoGateway;
	
	@Mock
	private ClienteGateway clienteGateway;
	
	@InjectMocks
	private EfetuarPagamentoUseCase efetuarPagamentoUseCase = new EfetuarPagamentoUseCaseImpl(pedidoGateway, plataformaPagamentoFactory, pagamentoGateway, clienteGateway);
	
	@Test
	void shouldSuccess() {
		
		final Long pedidoId = getRandomLong();
		final Long pagamentoId = getRandomLong();
		final Long clienteId = getRandomLong();
		final String pagamentoExternoId = getRandomString();
		final Double valorTotalPedido = 26.2D;//O valor é calculado e setado dentro do usecase
		
		EfetuarPagamentoParamDto paramsDto = createParams(pedidoId);
		
		List<ItemDto> itensDto = Arrays.asList(
				ItemDto.builder().id(getRandomLong()).quantidade(3L).valorUnitario(5D).build(),
				ItemDto.builder().id(getRandomLong()).quantidade(4L).valorUnitario(2.8D).build());
		PedidoDto pedidoDto = PedidoDto.builder().id(pedidoId).clienteId(clienteId)
				.statusId(0L)
				.valor(null)//O valor é calculado e setado dentro do usecase
				.itens(itensDto)
				.build();
		Optional<PedidoDto> pedidoOp = Optional.of(pedidoDto);
		doReturn(pedidoOp).when(pedidoGateway).obterPorId(pedidoId);

		ClienteDto clienteDto = ClienteDto.builder().id(clienteId).nome(getRandomString()).cpf(getRandomString()).email(getRandomString()).build();
		Optional<ClienteDto> clienteDtoOp = Optional.of(clienteDto);
		doReturn(clienteDtoOp).when(clienteGateway).obterPorId(clienteId);
		
		PlataformaPagamentoGateway plataformaPagamentoGatewayMock = Mockito.mock(PlataformaPagamentoGateway.class);
		doReturn(plataformaPagamentoGatewayMock).when(plataformaPagamentoFactory).obter();
		
		EnviaPagamentoReturnDto enviaPagamentoReturnDto = EnviaPagamentoReturnDto.builder().pagamentoExternoId(pagamentoExternoId).build();
		doReturn(enviaPagamentoReturnDto).when(plataformaPagamentoGatewayMock).enviarPagamento(any(EnviaPagamentoExternoParamDto.class));
		doReturn(pagamentoId).when(pagamentoGateway).criar(paramsDto.getPagamento());
		
		EfetuarPagamentoReturnDto returnDto = efetuarPagamentoUseCase.efetuar(paramsDto);
		
		assertEquals(pagamentoId, returnDto.getPagamentoId());
		assertEquals(pagamentoExternoId, returnDto.getPagamentoExternoId());

		assertEquals(valorTotalPedido, pedidoOp.get().getValor());
		assertEquals(valorTotalPedido, paramsDto.getPagamento().getValor());
		
		ArgumentCaptor<EnviaPagamentoExternoParamDto> enviaPagamentoExternoParamDtoAC = ArgumentCaptor.forClass(EnviaPagamentoExternoParamDto.class); 
		verify(plataformaPagamentoGatewayMock).enviarPagamento(enviaPagamentoExternoParamDtoAC.capture());
		
		EnviaPagamentoExternoParamDto enviaPagamentoExternoParamDtoSent = enviaPagamentoExternoParamDtoAC.getValue();

		assertEquals(ModoPagamento.PIX, enviaPagamentoExternoParamDtoSent.getModoPagamento());
		assertEquals(clienteDto.getEmail(), enviaPagamentoExternoParamDtoSent.getEmailCliente());
		assertEquals(clienteDto.getNome(), enviaPagamentoExternoParamDtoSent.getNomeCliente());
		assertEquals("", enviaPagamentoExternoParamDtoSent.getSobrenomeCliente());
		assertEquals("Combo de lanches", enviaPagamentoExternoParamDtoSent.getNomeProduto());
		assertEquals(1, enviaPagamentoExternoParamDtoSent.getParcelas());
		assertEquals(valorTotalPedido, enviaPagamentoExternoParamDtoSent.getValor());

		verify(pagamentoGateway).criar(paramsDto.getPagamento());
	}

	@Test
	void shouldPedidoNaoEncontradoException() {
		
		final Long pedidoId = getRandomLong();
		
		EfetuarPagamentoParamDto paramsDto = createParams(pedidoId);
		
		Optional<PedidoDto> pedidoOp = Optional.empty();
		doReturn(pedidoOp).when(pedidoGateway).obterPorId(pedidoId);
		
		assertThrows(PedidoNaoEncontradoException.class, () -> efetuarPagamentoUseCase.efetuar(paramsDto));
		
		verify(pagamentoGateway, never()).criar(paramsDto.getPagamento());
	}
	
	@Test
	void shouldClienteNaoEncontradoException() {
		
		final Long pedidoId = getRandomLong();
		final Long clienteId = getRandomLong();
		
		EfetuarPagamentoParamDto paramsDto = createParams(pedidoId);

		Optional<PedidoDto> pedidoOp = Optional.of(PedidoDto.builder().id(pedidoId).clienteId(clienteId).statusId(0L).build());
		doReturn(pedidoOp).when(pedidoGateway).obterPorId(pedidoId);
		
		Optional<ClienteDto> clienteOp = Optional.empty();
		doReturn(clienteOp).when(clienteGateway).obterPorId(clienteId);
		
		assertThrows(ClienteNaoEncontradoException.class, () -> efetuarPagamentoUseCase.efetuar(paramsDto));
	}
	
	@Test
	void shouldCamposObrigatoriosNaoPreechidoException() {
		EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder().pagamento(PagamentoDto.builder().build()).build();
		assertThrows(CamposObrigatoriosNaoPreechidoException.class, () -> efetuarPagamentoUseCase.efetuar(paramsDto));
	}
	
	private EfetuarPagamentoParamDto createParams(Long pedidoId) {

		PagamentoDto pagamentoDto = PagamentoDto.builder()
				.pedido(PedidoDto.builder().id(pedidoId).build())
				.formaPagamento("PIX")
				.build();
		EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder()
				.pagamento(pagamentoDto)
				.build();
		return paramsDto;
	}	
	
}
