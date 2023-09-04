package br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoJson {
	private Long id;
	private String pagamentoExternoId;
    private Long pedidoId;
    private String forma;
}
