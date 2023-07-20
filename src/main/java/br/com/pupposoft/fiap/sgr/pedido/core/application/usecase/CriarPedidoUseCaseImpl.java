package br.com.pupposoft.fiap.sgr.pedido.core.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ClienteServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ProdutoServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.PedidoItem;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ProdutoNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CriarPedidoUseCaseImpl implements CriarPedidoUseCase {

	private ClienteServiceGateway clienteServiceGateway;
	
	private ProdutoServiceGateway produtoServiceGateway;
	
	private PedidoRepositoryGateway pedidoRepositoryGateway;
	
	@Override
	public Long criar(PedidoDto pedidoDto) {
	    log.trace("Start pedidoDto={}", pedidoDto);
	    Pedido pedido = Pedido.builder()
	    		.dataCadastro(LocalDate.now())
	    		.build();

	    this.verificaRemoveClienteInexistente(pedido);
	    this.verificaExistenciaProduto(pedido);

	    pedido.setStatus(Status.RECEBIDO);

	    Long pedidoId = this.pedidoRepositoryGateway.criar(map(pedido));
	    log.trace("End pedidoId={}", pedidoId);
	    return pedidoId;
	}

	
	  private void verificaRemoveClienteInexistente(Pedido pedido) {
		  Optional<ClienteDto> clienteDtoOp = clienteServiceGateway.obterPorId(pedido.getCliente().getId());
		  if(clienteDtoOp.isEmpty()) {
			  pedido.removerCliente();
		  }
	  }

	  private void verificaExistenciaProduto(Pedido pedido) {
		  pedido
		  	.getItens().stream()
		  	.map(PedidoItem::getProduto)
		  	.forEach(produto -> {
		  		Optional<ProdutoDto> produtoOp = produtoServiceGateway.obterPorId(produto.getId());
		  		produtoOp.orElseThrow(() -> new ProdutoNotFoundException());
		  	});
	  }
	  
	  private PedidoDto map(Pedido pedido) {
		  
		  final ClienteDto cliente = pedido.temCliente() ? ClienteDto.builder().id(pedido.getCliente().getId()).build() : null;
		  final List<ItemDto> itens = pedido.getItens().stream().map(i -> ItemDto.builder()
						  .produto(ProdutoDto.builder().id(i.getProduto().getId()).build())
						  .quantidade(i.getQuantidade()).build())
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
