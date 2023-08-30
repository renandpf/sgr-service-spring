package br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.CartaoCreditoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class EnviaPagamentoExternoParamDto {
	private List<CartaoCreditoDto> cartoesCredito;//FIXME: REMOVER
	
	private String nomeProduto;
	private String nomeCliente;
	private String sobrenomeCliente;
	private String emailCliente;
	private Integer parcelas;
	private Double valor;
	private ModoPagamento modoPagamento;
	
}
