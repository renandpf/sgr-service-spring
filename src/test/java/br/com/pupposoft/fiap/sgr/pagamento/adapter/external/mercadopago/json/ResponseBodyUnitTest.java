package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

class ResponseBodyUnitTest {
	
	
	@Test
	void mapDomainStatusShouldAguardandoConfirmacaoPagamentoWhithPending() {
		ResponseBody responseBody = ResponseBody.builder()
				.status("pending")
				.build(); 
		
		assertEquals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO, responseBody.mapDomainStatus());
	}
	
	@Test
	void mapDomainStatusShouldAguardandoConfirmacaoPagamentoWithInProcess() {
		ResponseBody responseBody = ResponseBody.builder()
				.status("in_process")
				.build(); 
		
		assertEquals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO, responseBody.mapDomainStatus());
	}
	
	@Test
	void mapDomainStatusShouldAguardandoConfirmacaoPagamentoWithAuthorized() {
		ResponseBody responseBody = ResponseBody.builder()
				.status("authorized")
				.build(); 
		
		assertEquals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO, responseBody.mapDomainStatus());
	}
	
	@Test
	void mapDomainStatusShouldPagoWithApproved() {
		ResponseBody responseBody = ResponseBody.builder()
				.status("approved")
				.build(); 
		
		assertEquals(Status.PAGO, responseBody.mapDomainStatus());
	}

	@Test
	void mapDomainStatusShouldPagoWithAnyOthers() {
		ResponseBody responseBody = ResponseBody.builder()
				.status("cancelled")
				.build(); 
		
		assertEquals(Status.PAGAMENTO_INVALIDO, responseBody.mapDomainStatus());
	}
	
}
