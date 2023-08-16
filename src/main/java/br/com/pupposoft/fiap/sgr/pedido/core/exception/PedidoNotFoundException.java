package br.com.pupposoft.fiap.sgr.pedido.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class PedidoNotFoundException extends SystemBaseException {
	private static final long serialVersionUID = -950524946944182966L;
	
	private String code = "sgr.pedidoNotFound";
    private String message = "Pedido não foi encontrado";
    private Integer httpStatus = 404;

	
}
