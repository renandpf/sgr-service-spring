package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ClienteExistenteException extends SystemBaseException {
	private static final long serialVersionUID = -7934116874675268268L;
	
	private final String code = "sgr.clienteExistente";
	private final String message = "Cliente já cadastrado";
	private final Integer httpStatus = 400;
}
