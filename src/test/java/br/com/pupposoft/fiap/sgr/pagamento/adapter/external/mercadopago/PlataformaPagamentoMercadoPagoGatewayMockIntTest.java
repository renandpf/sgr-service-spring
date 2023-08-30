package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomDouble;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomInteger;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnect;

@WireMockTest
class PlataformaPagamentoMercadoPagoGatewayMockIntTest {


	@Test
	void enviarPagamentoWithSucess(WireMockRuntimeInfo wmRuntimeInfo) {
		
		final Integer idPagamentoExterno = getRandomInteger();
		final String responseBodyStr = "{\"id\": "+ idPagamentoExterno +"  }";
		final String accessToken = getRandomString();
		
		stubFor(post("/v1/payments").willReturn(ok(responseBodyStr)));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = new PlataformaPagamentoMercadoPagoGateway();
		setField(plataformaPagamentoGateway, "baseUrl", wmRuntimeInfo.getHttpBaseUrl());
		setField(plataformaPagamentoGateway, "accessToken", accessToken);
		setField(plataformaPagamentoGateway, "httpConnectGateway", new HttpConnect());
		setField(plataformaPagamentoGateway, "objectMapper", new ObjectMapper());
		
		EnviaPagamentoExternoParamDto paramsDto = EnviaPagamentoExternoParamDto.builder()
				.nomeProduto(getRandomString())
				.nomeCliente(getRandomString())
				.sobrenomeCliente(getRandomString())
				.emailCliente(getRandomString())
				.parcelas(getRandomInteger())
				.valor(getRandomDouble())
				.modoPagamento(ModoPagamento.PIX)
				.build();
		
		EnviaPagamentoReturnDto returnDto = plataformaPagamentoGateway.enviarPagamento(paramsDto);
		
		assertEquals(idPagamentoExterno+"", returnDto.getIdentificadorPagamento());
		
	}
	
	
}
