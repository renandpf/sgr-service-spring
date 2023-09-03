package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.Pagamento;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.AlteracaoStatusPedidoException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Pedido {
	private Long id;
    private Cliente cliente;
    private String observacao;
    private Status status;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
    private List<Item> itens;
    private List<Pagamento> pagamentos;

	
    public boolean temCliente() {
        return cliente != null;
    }
    
    //TODO: Aplicar SOLID: este método tende a crescer sempre q surgir novos status. 
    //Sugestão: "status" deveria ser uma classe (domain) ao invés de enum
	public void setStatus(Status newStatus) {
		
        switch (newStatus) {
        case RECEBIDO:
            if (status == null || status.equals(Status.RECEBIDO)) {
                status = newStatus;
                return;
            }
            throw new AlteracaoStatusPedidoException();

        case AGUARDANDO_CONFIRMACAO_PAGAMENTO:
            if (status.equals(Status.RECEBIDO)) {
                status = newStatus;
                break;
            }
            throw new AlteracaoStatusPedidoException();

        case PAGO:
            if (status.equals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO)) {
                status = newStatus;
                break;
            }
            throw new AlteracaoStatusPedidoException();
        
        case PAGAMENTO_INVALIDO:
        	if (status.equals(Status.AGUARDANDO_CONFIRMACAO_PAGAMENTO)) {
        		status = newStatus;
        		break;
        	}
        	throw new AlteracaoStatusPedidoException();

        case EM_PREPARACAO:
            if (status.equals(Status.PAGO)) {
                status = newStatus;
                break;
            }
            throw new AlteracaoStatusPedidoException();

        case PRONTO:
            if (status.equals(Status.EM_PREPARACAO)) {
                status = newStatus;
                dataConclusao = LocalDate.now();
                break;
            }
            throw new AlteracaoStatusPedidoException();

        case FINALIZADO:
            if (status.equals(Status.PRONTO)) {
                status = newStatus;
                break;
            }
            throw new AlteracaoStatusPedidoException();	
        }
	}
	
    public Status getStatus() {
        if (status == null) {
            status = Status.RECEBIDO;
        }
        return status;
    }

    public void removerCliente() {
        cliente = null;
    }

    public BigDecimal getValorTotal() {
    	return itens.stream().map(Item::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
