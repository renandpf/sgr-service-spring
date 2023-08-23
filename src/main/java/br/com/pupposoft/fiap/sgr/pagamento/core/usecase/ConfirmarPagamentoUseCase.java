package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

public interface ConfirmarPagamentoUseCase {
	void confirmar(String identificadorPagamento, String statusPagamento);
}
