package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

import java.math.BigDecimal;
import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Pagamento {
    private Long id;
    private Pedido pedido;
    private List<CartaoCredito> cartoesCredito;
    
    public BigDecimal getValor() {
    	return cartoesCredito.stream().map(CartaoCredito::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
}
