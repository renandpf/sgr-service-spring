package br.com.pupposoft.fiap.sgr.pagamento.core.application.ports;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;

public interface PagamentoRepositoryGateway {
	Long criar(PagamentoDto dto);
}
