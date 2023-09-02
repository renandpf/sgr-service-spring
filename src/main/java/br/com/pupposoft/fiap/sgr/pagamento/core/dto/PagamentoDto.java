package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PagamentoDto {
    private Long id;
    private String formaPagamento;

    @Setter
    private Double valor;
    
    @Setter
    private PedidoDto pedido;

    @Setter
    private String pagamentoExternoId;
}
