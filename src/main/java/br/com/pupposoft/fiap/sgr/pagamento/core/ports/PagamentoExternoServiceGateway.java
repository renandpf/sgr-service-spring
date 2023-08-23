package br.com.pupposoft.fiap.sgr.pagamento.core.ports;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public interface PagamentoExternoServiceGateway {
	EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto);
	Status mapStatus(String statusPagamento);

}
