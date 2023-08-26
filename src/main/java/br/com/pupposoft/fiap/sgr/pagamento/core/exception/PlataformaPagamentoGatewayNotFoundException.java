package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class PlataformaPagamentoGatewayNotFoundException extends SystemBaseException {
	private static final long serialVersionUID = 4605373878830208368L;
	
	private String code = "sgr.plataformaPagamentoGatewayNotFound";
	private String message = "Plataforma de pagamento não encontrada. Verifique a configuração do sistema";
	private Integer httpStatus = 500;
}
