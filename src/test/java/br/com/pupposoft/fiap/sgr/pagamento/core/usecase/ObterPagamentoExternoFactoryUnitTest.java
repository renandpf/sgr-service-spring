package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import static org.junit.Assert.assertTrue;
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

import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.PagamentoExternalHttpMercadoPago;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.PagamentoExternalHttpMock;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.PagamentoExternalHttpPagSeguro;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamentoExterna;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoExternoConfigGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoExternoGateway;

@ExtendWith(MockitoExtension.class)
public class ObterPagamentoExternoFactoryUnitTest {

	@InjectMocks
	private ObterPagamentoExternoFactory obterPagamentoExternoFactory;
	
	@Mock
	private PagamentoExternoConfigGateway pagamentoExternoConfigGateway;
	
	@BeforeEach
	void init() {
		List<PagamentoExternoGateway> plataformasPagamentoList = 
				Arrays.asList(new PagamentoExternalHttpMercadoPago(), new PagamentoExternalHttpMock(), new PagamentoExternalHttpPagSeguro());
		setField(obterPagamentoExternoFactory, "pagamentoExternoGatewayList", plataformasPagamentoList);
	}
	
	@Test
	void shouldMercadoPago() {
		
		final PlataformaPagamentoExterna plataformaPagamentoExterna = PlataformaPagamentoExterna.MERCADO_PAGO;
		PagamentoExternoConfigReturnDto returnDto = 
				PagamentoExternoConfigReturnDto.builder().plataformaPagamentoExterna(plataformaPagamentoExterna).build();
		doReturn(returnDto).when(pagamentoExternoConfigGateway).obter(any(PagamentoExternoConfigParamsDto.class));
		
		PagamentoExternoGateway pagamentoExternoGateway = obterPagamentoExternoFactory.obter();
		
		assertTrue(pagamentoExternoGateway instanceof PagamentoExternalHttpMercadoPago);
	}
}
