package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SistemaExterno {
	public String identificadorPagamento;
}
