package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CartaoCredito {
	public String numero;
    public String cvv;
    public String nome;
    public String cpf;
    public BigDecimal valor;
}
