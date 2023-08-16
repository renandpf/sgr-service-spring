package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PagamentoDto {
    private Long id;
    private List<CartaoCreditoDto> cartoesCredito;

    @Setter
    private BigDecimal valor;
    
    @Setter
    private PedidoDto pedido;

    @Setter
    private String identificadorPagamentoExterno;
    
    public boolean haveCartaoCredito() {
    	return cartoesCredito != null && !cartoesCredito.isEmpty();
    }
}
