package br.com.pupposoft.fiap.sgr.pagamento.adapter.repository;

import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoConfigGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PagamentoMySqlRepository implements PlataformaPagamentoConfigGateway {@Override
	
	public PlataformaPagamentoConfigReturnDto obter(PlataformaPagamentoConfigParamsDto params) {
		// TODO IMPLEMENTAR
		return null;
	}
}
