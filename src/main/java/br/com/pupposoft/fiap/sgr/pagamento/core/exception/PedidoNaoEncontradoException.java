package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class PedidoNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = -7939248652548082757L;
    
	private String code = "sgr.pedidoNotFound";
    private String message = "Pedido não foi encontrado";
    private Integer httpStatus = 404;
}
