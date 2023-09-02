package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.Pagamento;
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
        return this.cliente != null;
    }
    
	public void setStatus(Status status) {
		//TODO: IMPLEMENTAR
		
		this.status = status;
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
