package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

public interface ConfirmarPagamentoUseCase {
	void confirmar(String identificadorPagamento, String statusPagamento);
}
