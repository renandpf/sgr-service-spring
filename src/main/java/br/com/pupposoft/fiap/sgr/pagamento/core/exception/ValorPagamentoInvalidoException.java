package br.com.pupposoft.fiap.sgr.pagamento.core.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ValorPagamentoInvalidoException extends SystemBaseException {
	private static final long serialVersionUID = 8328412412727605361L;
	
	private String code = "sgr.valorPedidoInvalido";
    private String message = "Valor do pagamento está diferento do pedido.";
    private Integer httpStatus = 422;
}
