package br.com.pupposoft.fiap.sgr.pedido.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErrorToAccessProdutoServiceException extends SystemBaseException {
	private static final long serialVersionUID = 5751837643680491992L;

	private String code = "sgr.errorToAccessProductService";
    private String message = "Erro ao acessar o serviço produto";
    private Integer httpStatus = 500;
}
