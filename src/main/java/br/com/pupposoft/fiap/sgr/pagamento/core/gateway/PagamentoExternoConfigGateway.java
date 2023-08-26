package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigReturnDto;

public interface PagamentoExternoConfigGateway {
	PagamentoExternoConfigReturnDto obter(PagamentoExternoConfigParamsDto params);
}
