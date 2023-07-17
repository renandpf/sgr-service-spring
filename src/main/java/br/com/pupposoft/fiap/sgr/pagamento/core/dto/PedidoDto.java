package br.com.pupposoft.fiap.sgr.pagamento.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PedidoDto {
    public Long id;
    private Long statusId;
}
