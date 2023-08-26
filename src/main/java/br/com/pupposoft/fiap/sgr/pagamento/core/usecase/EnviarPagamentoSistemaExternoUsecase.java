package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class EnviarPagamentoSistemaExternoUsecase {

	public EnviaPagamentoReturnDto enviar(EnviaPagamentoExternoParamDto paramsDto) {
		
		//TODO: chamar um factory tal qual verifica na base de dados qual o sistema de pagamento vigente.
		
		return null;
	}
	
}
