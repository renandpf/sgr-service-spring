package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

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
}
