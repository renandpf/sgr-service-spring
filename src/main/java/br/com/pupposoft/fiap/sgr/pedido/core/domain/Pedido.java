package br.com.pupposoft.fiap.sgr.pedido.core.domain;

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
    
    public boolean contemItens() {
    	return itens != null && !itens.isEmpty();
    }
    
	public void setStatus(Status status) {
		//IMPLEMENTAR
		
	}
	
    public Status getStatus() {
        if (status == null) {
            status = Status.RECEBIDO;
        }
        return status;
    }

    public Long getTempoEsperaEmSegundos() {
    	//Implementar
    	return null;
    }

    public void removerCliente() {
        cliente = null;
    }

//    public Pedido(PedidoDto pedidoDto) {
//
//        Cliente cliente = null;
//        if (pedidoDto.getC) {
//            cliente = new Cliente(pedidoDto.cliente.id);
//        }
//
//        const pedido = new Pedido(
//          pedidoDto.id,
//          cliente,
//          pedidoDto.observacao,
//          pedidoDto.status,
//          pedidoDto.dataCadastro,
//          pedidoDto.dataConclusao
//        );
//
//        pedido.itens = pedidoDto.itens?.map(i => {
//            return new PedidoItem(
//              i.id,
//              pedido,
//              new Produto(i.produto.id),
//              i.quantidade,
//              i.valorUnitario
//            );
//        });
//
//        return pedido;
//    }

}
