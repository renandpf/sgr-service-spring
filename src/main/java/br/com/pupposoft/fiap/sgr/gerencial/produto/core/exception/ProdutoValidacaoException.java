package br.com.pupposoft.fiap.sgr.gerencial.produto.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ProdutoValidacaoException extends SystemBaseException {
	private static final long serialVersionUID = -2758617174295132221L;

	private final String code = "sgr.produtoValidacao";
	private String message = "";
	private final Integer httpStatus = 400;
	
	public ProdutoValidacaoException(String message) {
		this.message = message;
	}
}
