package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = {"numero", "cvv", "cpf"})
public class CartaoCreditoDto {
	private String numero;
    private String cvv;
    private String nome;
    private String cpf;
    private BigDecimal valor;
}
