package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PedidoDto {
    private Long id;
    private Long clienteId;
    private List<ItemDto> itens;
    
    @Setter
    private Double valor;
    
    @Setter
    private Long statusId;
}
