package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomLong;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.CartaoCreditoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;

@ExtendWith(MockitoExtension.class)
public class EfetuarPagamentoUseCaseUnitTest {
	
	@Mock
	private PedidoGateway pedidoGateway;
	
	@Mock
	private PlataformaPagamentoFactory plataformaPagamentoFactory;
	
	@Mock
	private PagamentoGateway pagamentoGateway;
	
	@InjectMocks
	private EfetuarPagamentoUseCase efetuarPagamentoUseCase = new EfetuarPagamentoUseCaseImpl(pedidoGateway, plataformaPagamentoFactory, pagamentoGateway);
	
	@Test
	void shouldSuccess() {
		
		final Long pedidoId = getRandomLong();
		final Long pagamentoId = getRandomLong();
		
		EfetuarPagamentoParamDto paramsDto = createParams(pedidoId);
		
		Optional<PedidoDto> pedidoOp = Optional.of(PedidoDto.builder().id(pedidoId).statusId(0L).build());
		doReturn(pedidoOp).when(pedidoGateway).obterPorId(pedidoId);
		
		PlataformaPagamentoGateway plataformaPagamentoGatewayMock = Mockito.mock(PlataformaPagamentoGateway.class);
		doReturn(plataformaPagamentoGatewayMock).when(plataformaPagamentoFactory).obter();
		
		EnviaPagamentoReturnDto enviaPagamentoReturnDto = EnviaPagamentoReturnDto.builder().identificadorPagamento(getRandomString()).build();
		doReturn(enviaPagamentoReturnDto).when(plataformaPagamentoGatewayMock).enviarPagamento(any(EnviaPagamentoExternoParamDto.class));
		
		doReturn(pagamentoId).when(pagamentoGateway).criar(paramsDto.getPagamento());
		
		EfetuarPagamentoReturnDto returnDto = efetuarPagamentoUseCase.efetuar(paramsDto);
		
		assertEquals(pagamentoId, returnDto.getPagamentoId());
		
		ArgumentCaptor<EnviaPagamentoExternoParamDto> enviaPagamentoExternoParamDtoAC = ArgumentCaptor.forClass(EnviaPagamentoExternoParamDto.class); 
		verify(plataformaPagamentoGatewayMock).enviarPagamento(enviaPagamentoExternoParamDtoAC.capture());
		
		EnviaPagamentoExternoParamDto enviaPagamentoExternoParamDtoSent = enviaPagamentoExternoParamDtoAC.getValue();
		assertEquals(paramsDto.getPagamento().getCartoesCredito(), enviaPagamentoExternoParamDtoSent.getCartoesCredito());

		verify(pagamentoGateway).criar(paramsDto.getPagamento());
		assertEquals(new BigDecimal("10"), paramsDto.getPagamento().getValor());
	}

	private EfetuarPagamentoParamDto createParams(Long pedidoId) {
		PagamentoDto pagamentoDto = PagamentoDto.builder()
				.pedido(PedidoDto.builder().id(pedidoId).build())
				.cartoesCredito(Arrays.asList(CartaoCreditoDto.builder().valor(new BigDecimal("10")).build()))
				.build();
		EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder()
				.pagamento(pagamentoDto)
				.build();
		return paramsDto;
	}	
	
}
