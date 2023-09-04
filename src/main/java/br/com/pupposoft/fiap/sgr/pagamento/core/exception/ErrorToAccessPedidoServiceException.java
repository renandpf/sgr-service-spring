package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessPedidoServiceException extends SystemBaseException {
	private static final long serialVersionUID = 2722650125602005636L;
	
	private String code = "sgr.errorToAccessClienteService";
	private String message = "Erro ao acessar cliente service";
	private Integer httpStatus = 500;
}
