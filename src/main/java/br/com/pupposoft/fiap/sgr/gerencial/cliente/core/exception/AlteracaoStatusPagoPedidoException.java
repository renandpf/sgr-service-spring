package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class AlteracaoStatusPagoPedidoException extends SystemBaseException {
	private static final long serialVersionUID = -247691740211854790L;
	
	private final String code = "sgr.alteracaoStatusPagoPedido";
	private String message = "";
	private final Integer httpStatus = 422;
	
	public AlteracaoStatusPagoPedidoException(String message){
        this.message = message;
    }


}
