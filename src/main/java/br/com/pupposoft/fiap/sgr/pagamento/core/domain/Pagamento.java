package br.com.pupposoft.fiap.sgr.pagamento.core.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Pagamento {
    private Long id;
    //private Pedido pedido;
    private List<CartaoCredito> cartoesCredito;
}
