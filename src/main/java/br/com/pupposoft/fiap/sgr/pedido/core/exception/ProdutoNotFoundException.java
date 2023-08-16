package br.com.pupposoft.fiap.sgr.pedido.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ProdutoNotFoundException extends SystemBaseException {
	private static final long serialVersionUID = 6885625333620163032L;
	
	private String code = "sgr.produtoNotFound";
    private String message = "Produto não foi encontrado";
    private Integer httpStatus = 404;
}
