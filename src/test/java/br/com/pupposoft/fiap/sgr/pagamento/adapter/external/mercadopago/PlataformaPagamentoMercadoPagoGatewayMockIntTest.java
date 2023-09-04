package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomDouble;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomInteger;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessPagamentoServicoExternoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.starter.http.HttpConnect;

@WireMockTest
class PlataformaPagamentoMercadoPagoGatewayMockIntTest {


	@Test
	void enviarPagamentoWithSucess(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		
		final Integer idPagamentoExterno = getRandomInteger();
		final String responseBodyStr = "{\"id\": "+ idPagamentoExterno +"  }";
		final String accessToken = getRandomString();
		final String path = "/v1/payments";
		
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
		
		assertEquals(idPagamentoExterno+"", returnDto.getPagamentoExternoId());
		
		verify(postRequestedFor(urlEqualTo(path)).withHeader("Content-Type", equalTo("application/json")));
		verify(postRequestedFor(urlEqualTo(path)).withHeader("Authorization", equalTo("Bearer " + accessToken)));
		
		List<ServeEvent> allServeEvents = getAllServeEvents();
		assertEquals(1, allServeEvents.size());
		
		RequestBodyJson requestBody = new ObjectMapper().readValue(allServeEvents.get(0).getRequest().getBodyAsString(), RequestBodyJson.class);
		
		assertEquals(paramsDto.getNomeProduto(), requestBody.getDescription());
		assertEquals(paramsDto.getNomeCliente(), requestBody.getPayer().getFirstName());
		assertEquals(paramsDto.getSobrenomeCliente(), requestBody.getPayer().getLastName());
		assertEquals(paramsDto.getEmailCliente(), requestBody.getPayer().getEmail());
		assertEquals(paramsDto.getParcelas(), requestBody.getInstallments());
		assertEquals(paramsDto.getValor(), requestBody.getTransactionAmount());
		assertEquals(paramsDto.getModoPagamento().name().toLowerCase(), requestBody.getPaymentMethodId());
		assertEquals("0", requestBody.getIssuerId());
	}
	
	@Test
	void enviarPagamentoWithError(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		
		final String accessToken = getRandomString();
		final String path = "/v1/payments";
		
		stubFor(post(path).willReturn(serverError()));
		
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
		
		assertThrows(ErrorToAccessPagamentoServicoExternoException.class, () -> plataformaPagamentoGateway.enviarPagamento(paramsDto));
	}
	
	@Test
	void obtemStatusWithSucess(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		
		final Integer idPagamentoExterno = getRandomInteger();
		final String responseBodyStr = 
				"{\n"
				+ "  \"id\": 1,\n"
				+ "  \"status\": \"approved\""
				+ "}";
		final String accessToken = getRandomString();
		final String path = "/v1/payments" + "/" + idPagamentoExterno;
		
		stubFor(get(path).willReturn(okJson(responseBodyStr)));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = new PlataformaPagamentoMercadoPagoGateway();
		setField(plataformaPagamentoGateway, "baseUrl", wmRuntimeInfo.getHttpBaseUrl());
		setField(plataformaPagamentoGateway, "accessToken", accessToken);
		setField(plataformaPagamentoGateway, "httpConnectGateway", new HttpConnect());
		setField(plataformaPagamentoGateway, "objectMapper", new ObjectMapper());
		
		Status statusReturned = plataformaPagamentoGateway.obtemStatus(idPagamentoExterno + "");
		
		assertEquals(Status.PAGO, statusReturned);
		
		verify(getRequestedFor(urlEqualTo(path)).withHeader("Content-Type", equalTo("application/json")));
		verify(getRequestedFor(urlEqualTo(path)).withHeader("Authorization", equalTo("Bearer " + accessToken)));
	}
	
	@Test
	void obtemStatusWithError(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
		final Integer idPagamentoExterno = getRandomInteger();
		final String accessToken = getRandomString();
		final String path = "/v1/payments" + "/" + idPagamentoExterno;
		
		stubFor(get(path).willReturn(serverError()));
		
		PlataformaPagamentoGateway plataformaPagamentoGateway = new PlataformaPagamentoMercadoPagoGateway();
		setField(plataformaPagamentoGateway, "baseUrl", wmRuntimeInfo.getHttpBaseUrl());
		setField(plataformaPagamentoGateway, "accessToken", accessToken);
		setField(plataformaPagamentoGateway, "httpConnectGateway", new HttpConnect());
		setField(plataformaPagamentoGateway, "objectMapper", new ObjectMapper());
		
		assertThrows(ErrorToAccessPagamentoServicoExternoException.class, () -> plataformaPagamentoGateway.obtemStatus(idPagamentoExterno + ""));
	}
	
}
