package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ClienteNaoEncontradoException extends SystemBaseException {
	private static final long serialVersionUID = 1114835481528583159L;
	
	private String code = "sgr.clienteNotFound";
    private String message = "Cliente não foi encontrado";
    private Integer httpStatus = 404;
}
