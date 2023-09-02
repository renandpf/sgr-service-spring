package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessClienteServiceException extends SystemBaseException {
	private static final long serialVersionUID = 2722650125602005636L;
	
	private String code = "sgr.errorToAccessPedidoService";
	private String message = "Erro ao acessar pedido service";
	private Integer httpStatus = 500;
}
