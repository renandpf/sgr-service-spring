package br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EfetuarPagamentoParamDto {
	private PagamentoDto pagamento;
}
