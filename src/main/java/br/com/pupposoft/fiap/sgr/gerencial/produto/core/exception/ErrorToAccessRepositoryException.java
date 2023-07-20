package br.com.pupposoft.fiap.sgr.gerencial.produto.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessRepositoryException extends SystemBaseException {
	private static final long serialVersionUID = 6729291256372725161L;
	
	private final String code = "sgr.errorToAccessRepository";
	private final String message = "Erro ao acessar repositório de dados";
	private final Integer httpStatus = 400;
}
