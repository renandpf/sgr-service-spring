package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomDouble;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomInteger;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json.RequestBodyJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnect;

@WireMockTest
class PlataformaPagamentoMercadoPagoGatewayMockIntTest {


	@Test
	void enviarPagamentoWithSucess(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		
		final Integer idPagamentoExterno = getRandomInteger();
		final String responseBodyStr = "{\"id\": "+ idPagamentoExterno +"  }";
		final String accessToken = getRandomString();
		final String path = "/v1/payments";
		
		//stubFor(post("/v1/payments").willReturn(ok(responseBodyStr)));
		stubFor(post(path).willReturn(okJson(responseBodyStr)));
		
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
		
		verify(postRequestedFor(urlEqualTo(path)).withHeader("Content-Type", equalTo("application/json")));
		
		List<ServeEvent> allServeEvents = getAllServeEvents();
		assertEquals(1, allServeEvents.size());
		
		RequestBodyJson requestBody = new ObjectMapper().readValue(allServeEvents.get(0).getRequest().getBodyAsString(), RequestBodyJson.class);
		
		assertEquals(paramsDto.getNomeProduto(), requestBody.getDescription());
		assertEquals(paramsDto.getNomeCliente(), requestBody.getPayer().getFirstName());
		assertEquals(paramsDto.getSobrenomeCliente(), requestBody.getPayer().getLastName());
		assertEquals(paramsDto.getEmailCliente(), requestBody.getPayer().getEmail());
		assertEquals(paramsDto.getParcelas(), requestBody.getInstallments());
		assertEquals(paramsDto.getValor(), requestBody.getTransactionAmount());
		assertEquals(paramsDto.getModoPagamento().name(), requestBody.getPaymentMethodId());
		assertEquals("SGR", requestBody.getIssuerId());
		
		String authorizationToken = allServeEvents.get(0).getRequest().getHeader("Authorization");
		assertEquals("Bearer " + accessToken, authorizationToken); 
	}
}
