package br.com.pupposoft.fiap.sgr.pedido.core.domain;

public enum Status {
    RECEBIDO,
    AGUARDANDO_CONFIRMACAO_PAGAMENTO,
    PAGO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO,
    PAGAMENTO_INVALIDO;
    
    public static Status get(Long id) {
    	Status[] values = Status.values();
    	return values[id.intValue()]; 
    }
    
    public static Long get(Status status) {
    	return (long) status.ordinal();
    }
}
