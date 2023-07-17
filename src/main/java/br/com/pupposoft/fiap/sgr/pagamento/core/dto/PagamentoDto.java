package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PagamentoDto {
    private Long id;
    private PedidoDto pedido;
    private List<CartaoCreditoDto> cartoesCredito;
    private String identificadorPagamentoExterno;

}
