package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PedidoDto {
	private Long id;
    private String observacao;
    private Long statusId;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
    private ClienteDto cliente;
    private List<ItemDto> itens;
    private List<PagamentoDto> pagamentos;
    
    public boolean hasCliente() {
    	return cliente != null;
    }
    
    public boolean hasItens() {
    	return itens != null && !itens.isEmpty();
    }

    public boolean hasPagamentos() {
    	return pagamentos != null && !pagamentos.isEmpty();
    }
    
    
}
