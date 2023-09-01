package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;

public abstract class PlataformaPagamentoGateway {
	public PlataformaPagamento plataformaPagamentoExterna;
	
	public abstract EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto);
	public abstract Status obtemStatus(String identificadorPagamento);

	public boolean isElegivel(PlataformaPagamento plataformaPagamentoExterna) {
		return plataformaPagamentoExterna.equals(this.plataformaPagamentoExterna);
	}
}
