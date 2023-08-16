package br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;

public interface EfetuarPagamentoUseCase {
	EfetuarPagamentoReturnDto efetuar(EfetuarPagamentoParamDto dto);
}
