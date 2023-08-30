package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.PlataformaPagamentoMockGateway;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.PlataformaPagamentoPagSeguroGateway;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.PlataformaPagamentoMercadoPagoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PlataformaPagamentoGatewayNotFoundException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoConfigGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;

@ExtendWith(MockitoExtension.class)
class ObterPagamentoExternoFactoryUnitTest {

	@InjectMocks
	private PlataformaPagamentoFactory plataformaPagamentoFactory;
	
	@Mock
	private PlataformaPagamentoConfigGateway plataformaPagamentoConfigGateway;
	
	@BeforeEach
	void init() {
		List<PlataformaPagamentoGateway> plataformasPagamentoList = 
				Arrays.asList(
						new PlataformaPagamentoMercadoPagoGateway(), 
						new PlataformaPagamentoMockGateway(), 
						new PlataformaPagamentoPagSeguroGateway());
		setField(plataformaPagamentoFactory, "plataformaPagamentoGatewayList", plataformasPagamentoList);
	}
	
	@Test
	void shouldMercadoPago() {
		
		final PlataformaPagamento plataformaPagamento = PlataformaPagamento.MERCADO_PAGO;
		PlataformaPagamentoConfigReturnDto returnDto = 
				PlataformaPagamentoConfigReturnDto.builder().plataformaPagamento(plataformaPagamento).build();
		doReturn(returnDto).when(plataformaPagamentoConfigGateway).obter(any(PlataformaPagamentoConfigParamsDto.class));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = plataformaPagamentoFactory.obter();
		
		assertTrue(plataformaPagamentoGateway instanceof PlataformaPagamentoMercadoPagoGateway);
	}
	
	@Test
	void shouldPagSeguro() {
		
		final PlataformaPagamento plataformaPagamento = PlataformaPagamento.PAG_SEGURO;
		PlataformaPagamentoConfigReturnDto returnDto = 
				PlataformaPagamentoConfigReturnDto.builder().plataformaPagamento(plataformaPagamento).build();
		doReturn(returnDto).when(plataformaPagamentoConfigGateway).obter(any(PlataformaPagamentoConfigParamsDto.class));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = plataformaPagamentoFactory.obter();
		
		assertTrue(plataformaPagamentoGateway instanceof PlataformaPagamentoPagSeguroGateway);
	}
	
	@Test
	void shouldMock() {
		
		final PlataformaPagamento plataformaPagamento = PlataformaPagamento.MOCK;
		PlataformaPagamentoConfigReturnDto returnDto = 
				PlataformaPagamentoConfigReturnDto.builder().plataformaPagamento(plataformaPagamento).build();
		doReturn(returnDto).when(plataformaPagamentoConfigGateway).obter(any(PlataformaPagamentoConfigParamsDto.class));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = plataformaPagamentoFactory.obter();
		
		assertTrue(plataformaPagamentoGateway instanceof PlataformaPagamentoMockGateway);
	}
	
	@Test
	void shouldPlataformaPagamentoGatewayNotFoundException() {
		
		setField(plataformaPagamentoFactory, "plataformaPagamentoGatewayList", Arrays.asList());
		
		final PlataformaPagamento plataformaPagamento = PlataformaPagamento.MOCK;
		PlataformaPagamentoConfigReturnDto returnDto = 
				PlataformaPagamentoConfigReturnDto.builder().plataformaPagamento(plataformaPagamento).build();
		doReturn(returnDto).when(plataformaPagamentoConfigGateway).obter(any(PlataformaPagamentoConfigParamsDto.class));
		
		assertThrows(PlataformaPagamentoGatewayNotFoundException.class, () -> plataformaPagamentoFactory.obter());
	}
	
	
}
