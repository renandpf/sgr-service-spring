package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamentoExterna;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public abstract class PagamentoExternoGateway {
	public PlataformaPagamentoExterna plataformaPagamentoExterna;
	
	public abstract EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto);
	public abstract Status mapStatus(String statusPagamento);

	public boolean isElegivel(PlataformaPagamentoExterna plataformaPagamentoExterna) {
		return plataformaPagamentoExterna.equals(this.plataformaPagamentoExterna);
	}
}
