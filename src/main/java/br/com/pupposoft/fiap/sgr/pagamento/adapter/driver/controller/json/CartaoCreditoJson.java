package br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CartaoCreditoJson {
    private String numero;
    private String cvv;
    private String nome;
    private String cpf;
    private BigDecimal valor;
}
