package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CartaoCredito {
	private String numero;
    private String cvv;
    private String nome;
    private String cpf;
    private BigDecimal valor;
}
