package br.com.pupposoft.fiap.sgr.pedido.adapter.web.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class ItemJson {
	private Long id;
	private Long quantidade;
	private Long produtoId;
	private String produtoNome;
	private Double valorUnitario;
}
