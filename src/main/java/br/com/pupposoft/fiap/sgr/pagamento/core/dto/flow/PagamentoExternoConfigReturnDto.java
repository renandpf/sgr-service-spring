package br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamentoExterna;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PagamentoExternoConfigReturnDto {
	private PlataformaPagamentoExterna plataformaPagamentoExterna;
}
