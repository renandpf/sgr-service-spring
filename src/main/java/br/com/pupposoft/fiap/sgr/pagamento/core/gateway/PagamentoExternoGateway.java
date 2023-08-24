package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public interface PagamentoExternoGateway {
	EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto);
	Status mapStatus(String statusPagamento);

}
