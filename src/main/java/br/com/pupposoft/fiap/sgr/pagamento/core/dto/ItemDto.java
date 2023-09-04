package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ItemDto {
	private Long id;
	private Long quantidade;
	private Long produtoId;
	private String produtoNome;
	private Double valorUnitario;
}
