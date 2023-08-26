package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigReturnDto;

public interface PlataformaPagamentoConfigGateway {
	PlataformaPagamentoConfigReturnDto obter(PlataformaPagamentoConfigParamsDto params);
}
