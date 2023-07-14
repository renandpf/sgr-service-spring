package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ExclusaoProdutoAssociadoPedidoException extends SystemBaseException {
	private static final long serialVersionUID = -5309107784754026551L;
	
	private final String code = "sgr.exclusionProductAssociatedWithOrder";
	private final String message = "O produto está associado a pedido(s)";
	private final Integer httpStatus = 422;
}
