package br.com.pupposoft.fiap.sgr.pedido.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessClienteServiceException extends SystemBaseException {
	private static final long serialVersionUID = 110100714018732342L;

	private String code = "sgr.errorToAccessClientService";
    private String message = "Erro ao acessar o serviço cliente";
    private Integer httpStatus = 500;

	
}
