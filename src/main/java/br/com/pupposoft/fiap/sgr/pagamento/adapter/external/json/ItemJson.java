package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ItemJson {
	private Long id;
	private Long quantidade;
	private Long produtoId;
	private String produtoNome;
	private Double valorUnitario;

}
