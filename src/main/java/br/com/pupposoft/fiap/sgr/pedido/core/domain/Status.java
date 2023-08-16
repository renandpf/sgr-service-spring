package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import java.util.Arrays;

public enum Status {
    RECEBIDO,
    AGUARDANDO_CONFIRMACAO_PAGAMENTO,
    PAGO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO,
    PAGAMENTO_INVALIDO;
    
    public static Status get(Long id) {
    	return Arrays.stream(Status.values())
    			.filter(s -> s.ordinal() == id)
    			.findAny().orElseThrow();
    }
    
    public static Long get(Status status) {
    	return (long) status.ordinal();
    }
}
