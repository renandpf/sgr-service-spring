package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamentoExterna;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PagamentoExternoConfigReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoExternoConfigGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoExternoGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ObterPagamentoExternoFactory {

	private PagamentoExternoConfigGateway pagamentoExternoConfigGateway;
	
	private List<PagamentoExternoGateway> pagamentoExternoGatewayList;
	
	public PagamentoExternoGateway obter() {
		log.trace("Start");
		PagamentoExternoConfigReturnDto returnDto = pagamentoExternoConfigGateway.obter(PagamentoExternoConfigParamsDto.builder().build());
		
		PlataformaPagamentoExterna ppe = returnDto.getPlataformaPagamentoExterna();
		
		Optional<PagamentoExternoGateway> pagamentoExternoGatewayOp = pagamentoExternoGatewayList.stream().filter(pp -> pp.isElegivel(ppe)).findAny();
		
		if(pagamentoExternoGatewayOp.isEmpty()) {
			log.warn("Plataforma de Pagamento Gateway não encontrada");
			//TODO: LANÇAR EXCEÇÃO
		}
		
		PagamentoExternoGateway pagamentoExternoGateway = pagamentoExternoGatewayOp.get();
		
		log.trace("End pagamentoExternoGateway={}", pagamentoExternoGateway);
		return pagamentoExternoGateway;
	}
	
}
