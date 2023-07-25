package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.ItemEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.ItemEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.PedidoEntityRepository;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessRepositoryException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PedidoMySqlRepositoryGateway implements PedidoRepositoryGateway {

	private PedidoEntityRepository pedidoEntityRepository;
	
	private ItemEntityRepository itemEntityRepository;
	
	@Override
	public Long criar(PedidoDto pedido) {
        try {
            log.trace("Start pedido={}", pedido);

            PedidoEntity pedidoEntity = mapDtoToEntity(pedido);
            pedidoEntityRepository.save(pedidoEntity);
            Long pedidoCreatedId = pedidoEntity.getId();

            pedidoEntity.getItens().forEach(ie -> itemEntityRepository.save(ie));
            log.trace("End pedidoCreatedId={}", pedidoCreatedId);
            return pedidoCreatedId;

        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public void atualizarStatus(PedidoDto pedido) {
        try {
            log.trace("Start pedido={}", pedido);
            this.pedidoEntityRepository.save(
            		PedidoEntity.builder().
            			id(pedido.getId())
            			.statusId(pedido.getStatusId())
            		.build());
            log.trace("End");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
        try {
            log.trace("Start pedidoId={}", pedidoId);
            Optional<PedidoEntity> pedidoEntityOp = this.pedidoEntityRepository.findById(pedidoId);
            
            Optional<PedidoDto> pedidoDtoOp = Optional.empty();
            if(pedidoEntityOp.isPresent()) {
            	PedidoEntity pedidoEntity = pedidoEntityOp.get();
            	
            	PedidoDto pedidoDto = mapEntityToDto(pedidoEntity);
            	
            	pedidoDtoOp = Optional.of(pedidoDto);
            }
            
            
            log.trace("End pedidoDtoOp={}", pedidoDtoOp);
            return pedidoDtoOp;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }
	}

	@Override
	public List<PedidoDto> obterPorStatus(List<Status> statusList) {
        try {
            log.trace("Start statusList={}", statusList);
            
            List<Long> statusIdList = statusList.stream().mapToLong(s -> Status.get(s)).boxed().toList();
            
            List<PedidoEntity> pedidoEntityList = this.pedidoEntityRepository.findByStatusIdIn(statusIdList);

            List<PedidoDto> pedidosDtos = pedidoEntityList.stream().map(this::mapEntityToDto).toList();
            
            log.trace("End pedidosDtos={}", pedidosDtos);
            return pedidosDtos;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessRepositoryException();
        }	
    }

	private PedidoDto mapEntityToDto(PedidoEntity pedidoEntity) {
		ClienteDto clienteDto = ClienteDto.builder().build();
		List<ItemDto> itensDto = pedidoEntity.getItens().stream()
				.map(ie -> ItemDto.builder()
						.id(ie.getId())
						.quantidade(ie.getQuantidade())
						.produto(ProdutoDto.builder()
								.id(ie.getProduto().getId())
								.nome(ie.getProduto().getNome())
								.build())
				.build())
			.toList();
		
		PedidoDto pedidoDto = PedidoDto.builder()
			.id(pedidoEntity.getId())
			.observacao(pedidoEntity.getObservacao())
			.statusId(pedidoEntity.getStatusId())
			.dataCadastro(pedidoEntity.getDataCadastro())
			.dataConclusao(pedidoEntity.getDataConclusao())
			.cliente(clienteDto)
			.itens(itensDto)
		.build();
		return pedidoDto;
	}
	
	private PedidoEntity mapDtoToEntity(PedidoDto pedidoDto) {
		
		ClienteEntity clienteEntity = null;
		if(pedidoDto.hasCliente()) {
			ClienteDto clienteDto = pedidoDto.getCliente();
			clienteEntity = ClienteEntity.builder()
					.id(clienteDto.getId())
					.nome(clienteDto.getNome())
					.cpf(clienteDto.getCpf())
					.email(clienteDto.getEmail())
					.build();
		}
		
		List<ItemEntity> itens = new ArrayList<>();
		if(pedidoDto.hasItens()) {
			itens = pedidoDto.getItens().stream().map(i -> {
				return ItemEntity.builder()
						.id(null)
						.quantidade(null)
						.valor(null)
						.produto(ProdutoEntity.builder().id(null).build())
						.build();
			}).toList();
		}
		
		List<PagamentoEntity> pagamentos = new ArrayList<>();
		if(pedidoDto.hasPagamentos()) {
			pagamentos = pedidoDto.getPagamentos().stream().map(p -> {
				return PagamentoEntity.builder()
						.id(p.getId())
						.identificadorPagamentoExterno(p.getIdentificadorPagamento())
						.build();
			}).toList();
		}
		
		return PedidoEntity.builder()
				.id(pedidoDto.getId())
				.statusId(pedidoDto.getStatusId())
				.dataCadastro(pedidoDto.getDataCadastro())
				.dataConclusao(pedidoDto.getDataConclusao())
				.observacao(pedidoDto.getObservacao())
				.cliente(clienteEntity)
				.itens(itens)
				.pagamentos(pagamentos)
				.build();
		
	}	
	
}
