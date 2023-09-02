package br.com.pupposoft.fiap.sgr.pedido.core.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Produto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Item;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ProdutoNotFoundException;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.ClienteGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.ProdutoGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CriarPedidoUseCaseImpl implements CriarPedidoUseCase {

	private ClienteGateway clienteGateway;
	
	private ProdutoGateway produtoGateway;
	
	private PedidoGateway pedidoGateway;
	
	@Override
	public Long criar(PedidoDto pedidoDto) {
	    log.trace("Start pedidoDto={}", pedidoDto);
	    Pedido pedido = mapDtoToDomain(pedidoDto);

	    this.verificaRemoveClienteInexistente(pedido);
	    this.carregaProdutosIntoPedido(pedido);

	    pedido.setStatus(Status.RECEBIDO);//Execução de regras dentro do domain

	    Long pedidoId = this.pedidoGateway.criar(mapDomainToDto(pedido));
	    log.trace("End pedidoId={}", pedidoId);
	    return pedidoId;
	}


	private Pedido mapDtoToDomain(PedidoDto pedidoDto) {
		return Pedido.builder()
	    		.cliente(pedidoDto.hasCliente() ? Cliente.builder().id(pedidoDto.getCliente().getId()).build() : null)
	    		.dataCadastro(LocalDate.now())
	    		.observacao(pedidoDto.getObservacao())
	    		.itens(pedidoDto.getItens().stream().map(i -> Item.builder()
	    				.id(i.getId())
	    				.quantidade(i.getQuantidade())
	    				.produto(Produto.builder().id(i.getProduto().getId()).build())
	    				.build()).toList())
	    		.build();
	}

	
	  private void verificaRemoveClienteInexistente(Pedido pedido) {
		  Optional<ClienteDto> clienteDtoOp = clienteGateway.obterPorId(pedido.getCliente().getId());
		  if(clienteDtoOp.isEmpty()) {
			  pedido.removerCliente();
		  }
	  }

	  private void carregaProdutosIntoPedido(Pedido pedido) {
		  pedido.getItens().forEach(i -> {
		  		Produto produto = i.getProduto();
			  	Optional<ProdutoDto> produtoOp = produtoGateway.obterPorId(produto.getId());
		  		ProdutoDto pDto = produtoOp.orElseThrow(() -> new ProdutoNotFoundException());
		  		
		  		i.setProduto(Produto.builder()
	  			.id(pDto.getId())
	  			.nome(pDto.getNome())
	  			.descricao(pDto.getDescricao())
	  			.categoria(Categoria.valueOf(pDto.getCategoria()))
	  			.valor(pDto.getValor()).build());
		  	});
	  }
	  
	  private PedidoDto mapDomainToDto(Pedido pedido) {

		  final ClienteDto cliente = pedido.temCliente() ? ClienteDto.builder().id(pedido.getCliente().getId()).build() : null;
		  final List<ItemDto> itens = pedido
				  .getItens()
				  .stream()
				  .map(i -> ItemDto.builder()
						  .produto(ProdutoDto.builder().id(i.getProduto().getId()).build())
						  .quantidade(i.getQuantidade())
						  .valorUnitario(i.getProduto().getValor().doubleValue())
						  .build())

				  .toList();

		  return PedidoDto.builder()
				  .id(pedido.getId())
				  .observacao(pedido.getObservacao())
				  .statusId(Status.get(pedido.getStatus()))
				  .dataCadastro(pedido.getDataCadastro())
				  .cliente(cliente)
				  .itens(itens)

				  .build();
	  }
}
