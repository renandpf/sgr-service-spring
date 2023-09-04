package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.pupposoft.fiap.sgr.pedido.core.exception.AlteracaoStatusPedidoException;

class PedidoUnitTest {
	
	@Test
	void setStatusToRecebidoSucess() {
		Pedido pedido = Pedido.builder().build();
		pedido.setStatus(Status.RECEBIDO);
		assertEquals(Status.RECEBIDO, pedido.getStatus());
		
		pedido = Pedido.builder().status(Status.RECEBIDO).build();
		pedido.setStatus(Status.RECEBIDO);
		assertEquals(Status.RECEBIDO, pedido.getStatus());
	}
	
	@Test
	void setStatusToRecebidoError() {
		final Pedido pedido = Pedido.builder().status(Status.FINALIZADO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.RECEBIDO));
	}
	
	@Test
	void setStatusToAguardandoConfirmacaoPagamentoSucess() {
		Pedido pedido = Pedido.builder().status(Status.RECEBIDO).build();
		pedido.setStatus(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO);
		assertEquals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO, pedido.getStatus());
	}
	
	@Test
	void setStatusToAguardandoConfirmacaoPagamentoError() {
		final Pedido pedido = Pedido.builder().status(Status.FINALIZADO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO));
	}

	@Test
	void setStatusToPagoSucess() {
		Pedido pedido = Pedido.builder().status(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO).build();
		pedido.setStatus(Status.PAGO);
		assertEquals(Status.PAGO, pedido.getStatus());
	}
	
	@Test
	void setStatusToPagoError() {
		final Pedido pedido = Pedido.builder().status(Status.FINALIZADO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.PAGO));
	}
	
	@Test
	void setStatusToEmPreparacaoSucess() {
		Pedido pedido = Pedido.builder().status(Status.PAGO).build();
		pedido.setStatus(Status.EM_PREPARACAO);
		assertEquals(Status.EM_PREPARACAO, pedido.getStatus());
	}
	
	@Test
	void setStatusToEmPreparacaoError() {
		final Pedido pedido = Pedido.builder().status(Status.FINALIZADO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.EM_PREPARACAO));
	}
	
	@Test
	void setStatusToProntoSucess() {
		Pedido pedido = Pedido.builder().status(Status.EM_PREPARACAO).build();
		pedido.setStatus(Status.PRONTO);
		assertEquals(Status.PRONTO, pedido.getStatus());
	}
	
	@Test
	void setStatusToProntoError() {
		final Pedido pedido = Pedido.builder().status(Status.FINALIZADO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.PRONTO));
	}
	
	@Test
	void setStatusToFinalizadoSucess() {
		Pedido pedido = Pedido.builder().status(Status.PRONTO).build();
		pedido.setStatus(Status.FINALIZADO);
		assertEquals(Status.FINALIZADO, pedido.getStatus());
	}
	
	@Test
	void setStatusToFinalizadoError() {
		final Pedido pedido = Pedido.builder().status(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.FINALIZADO));
	}
	
	@Test
	void setStatusToPagamentoInvalidoSucess() {
		Pedido pedido = Pedido.builder().status(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO).build();
		pedido.setStatus(Status.PAGAMENTO_INVALIDO);
		assertEquals(Status.PAGAMENTO_INVALIDO, pedido.getStatus());
	}
	
	@Test
	void setStatusToPagamentoInvalidoError() {
		final Pedido pedido = Pedido.builder().status(Status.RECEBIDO).build();
		assertThrows(AlteracaoStatusPedidoException.class, () -> pedido.setStatus(Status.PAGAMENTO_INVALIDO));
	}
	
}
