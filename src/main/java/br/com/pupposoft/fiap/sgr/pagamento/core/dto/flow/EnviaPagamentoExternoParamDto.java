package br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.ModoPagamento;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class EnviaPagamentoExternoParamDto {
	private String nomeProduto;
	private String nomeCliente;
	private String sobrenomeCliente;
	private String emailCliente;
	private Integer parcelas;
	private Double valor;
	private ModoPagamento modoPagamento;
	
}
