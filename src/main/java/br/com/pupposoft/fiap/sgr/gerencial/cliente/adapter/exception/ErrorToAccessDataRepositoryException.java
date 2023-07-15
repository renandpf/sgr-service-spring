package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessDataRepositoryException extends SystemBaseException {
	private static final long serialVersionUID = 4584096877599032115L;
	
	private final String code = "sgr.errorToAccessRepository";
	private final String message = "Erro ao acessar repositório de dados";
	private final Integer httpStatus = 400;
}
