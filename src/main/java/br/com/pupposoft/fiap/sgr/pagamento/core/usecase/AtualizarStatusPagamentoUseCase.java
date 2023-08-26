package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;

public interface AtualizarStatusPagamentoUseCase {
	void atualizar(PlataformaPagamento plataformaPagamento, String identificadorPagamento);
}
