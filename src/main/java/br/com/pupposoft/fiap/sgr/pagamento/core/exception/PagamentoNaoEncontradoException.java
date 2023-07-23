package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class PagamentoNaoEncontradoException  extends SystemBaseException {
	private static final long serialVersionUID = 4897654116423529847L;
	
	private final String code = "sgr.pagamentoNaoEncontrado";
	private final String message = "Pagamento não encontrado";
	private final Integer httpStatus = 404;

}
