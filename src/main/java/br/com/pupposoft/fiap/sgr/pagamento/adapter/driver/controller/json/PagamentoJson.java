package br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PagamentoJson {
    private Long pedidoId;
    private List<CartaoCreditoJson> cartoesCreditos;
    
    public PagamentoDto getDto() {
    	//TODO: implementar!
//        const cartoesCredito = this.cartoesCreditos.map(cc => new CartaoCreditoDto(cc.numero, cc.cvv, cc.nome, cc.cpf, cc.valor));
//        const pedido = new PedidoDto(this.pedidoId, undefined as unknown as number);
//        return new PagamentoDto(undefined, pedido, cartoesCredito);
    	
    	return null;
    }
    
}
