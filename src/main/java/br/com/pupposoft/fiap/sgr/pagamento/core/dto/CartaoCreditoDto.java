package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = {"numero", "cvv", "cpf"})
public class CartaoCreditoDto {
	public String numero;
    public String cvv;
    public String nome;
    public String cpf;
    public BigDecimal valor;
}
