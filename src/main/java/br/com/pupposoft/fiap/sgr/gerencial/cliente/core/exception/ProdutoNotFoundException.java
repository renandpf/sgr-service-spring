package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ProdutoNotFoundException extends SystemBaseException {
	private static final long serialVersionUID = -9028673366453394544L;
	private final String code = "sgr.produtoNotFound";
	private final String message = "Produto não foi encontrado";
	private final Integer httpStatus = 404;
}
