package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;

public interface ObterPagamentoUsecase {

	PagamentoDto obterPorIdentificadorPagamento(String identificadorPagamento);

}
