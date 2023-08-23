package br.com.pupposoft.fiap.sgr.pedido.core.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Produto;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Pedido;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Item;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ProdutoNotFoundException;
import br.com.pupposoft.fiap.sgr.pedido.core.port.ClienteServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.port.ProdutoServiceGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CriarPedidoUseCaseImpl implements CriarPedidoUseCase {

	@Autowired
	private ClienteServiceGateway clienteServiceGateway;
	
	@Autowired
	private ProdutoServiceGateway produtoServiceGateway;
	
	@Autowired
	private PedidoRepositoryGateway pedidoRepositoryGateway;
	
	@Override
	public Long criar(PedidoDto pedidoDto) {
	    log.trace("Start pedidoDto={}", pedidoDto);
	    Pedido pedido = mapDtoToDomain(pedidoDto);

	    this.verificaRemoveClienteInexistente(pedido);
	    this.verificaExistenciaProduto(pedido);

	    pedido.setStatus(Status.RECEBIDO);

	    Long pedidoId = this.pedidoRepositoryGateway.criar(mapDomainToDto(pedido));
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
		  Optional<ClienteDto> clienteDtoOp = clienteServiceGateway.obterPorId(pedido.getCliente().getId());
		  if(clienteDtoOp.isEmpty()) {
			  pedido.removerCliente();
		  }
	  }

	  private void verificaExistenciaProduto(Pedido pedido) {
		  pedido
		  	.getItens().stream()
		  	.map(Item::getProduto)
		  	.forEach(produto -> {
		  		Optional<ProdutoDto> produtoOp = produtoServiceGateway.obterPorId(produto.getId());
		  		produtoOp.orElseThrow(() -> new ProdutoNotFoundException());
		  	});
	  }
	  
	  private PedidoDto mapDomainToDto(Pedido pedido) {
		  
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
