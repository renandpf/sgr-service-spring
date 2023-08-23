package br.com.pupposoft.fiap.sgr.pagamento.adapter.controller.json;

import java.util.List;

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
	private String identificadorPagamento;
    private Long pedidoId;
    private List<CartaoCreditoJson> cartoesCreditos;
}
