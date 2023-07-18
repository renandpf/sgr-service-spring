package br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CartaoCreditoJson {
    public String numero;
    public String cvv;
    public String nome;
    public String cpf;
    public BigDecimal valor;
}
