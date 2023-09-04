package br.com.pupposoft.fiap.sgr.pedido.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class AlteracaoStatusPedidoException extends SystemBaseException {
	private static final long serialVersionUID = -1953859612464719674L;
	private String code = "sgr.alteracaoStatusPedido";
    private String message = "O status atual do pedido não permite essa alteração.";
    private Integer httpStatus = 422;
}
