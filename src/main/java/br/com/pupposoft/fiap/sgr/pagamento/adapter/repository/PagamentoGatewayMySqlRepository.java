package br.com.pupposoft.fiap.sgr.pagamento.adapter.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.repository.PagamentoEntityRepository;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessRepositoryException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PagamentoGatewayMySqlRepository implements PagamentoGateway {

	@Autowired
	private PagamentoEntityRepository pagamentoRepository;

	@Override
	public Long criar(PagamentoDto pagamentoDto) {
		try {
			log.trace("Start pagamento={}", pagamentoDto);

			PagamentoEntity pagamentoEntity = PagamentoEntity.builder()
					.identificadorPagamentoExterno(pagamentoDto.getPagamentoExternoId())
					.valor(pagamentoDto.getValor())
					.pedido(PedidoEntity.builder().id(pagamentoDto.getPedido().getId()).build())
					.build();

			PagamentoEntity pagamentoEntityCreated = pagamentoRepository.save(pagamentoEntity);
			Long pagamentoEntityCreatedId = pagamentoEntityCreated.getId();

			log.trace("End pagamentoEntityCreatedId={}", pagamentoEntityCreatedId);
			return pagamentoEntityCreatedId;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}

	@Override
	public Optional<PagamentoDto> obterPorIdentificadorPagamento(String pagamentoExternoId) {
		try {
			log.trace("Start pagamentoExternoId={}", pagamentoExternoId);


			Optional<PagamentoEntity> pagamentoEntityOp = pagamentoRepository.findByIdentificadorPagamentoExterno(pagamentoExternoId);

			Optional<PagamentoDto> pagamentoDtoOp = Optional.empty();
			if(pagamentoEntityOp.isPresent()) {
				PagamentoEntity pagamentoEntity = pagamentoEntityOp.get();
				PagamentoDto pagamentoDto = mapEntityToDto(pagamentoExternoId, pagamentoEntity);
				pagamentoDtoOp = Optional.of(pagamentoDto);
			}

			log.trace("End pagamentoDtoOp={}", pagamentoDtoOp);
			return pagamentoDtoOp;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}	
	}

	private PagamentoDto mapEntityToDto(String pagamentoExternoId, PagamentoEntity pagamentoEntity) {
		PagamentoDto pagamentoDto = PagamentoDto.builder()
				.id(pagamentoEntity.getId())
				.pagamentoExternoId(pagamentoExternoId)
				.pedido(PedidoDto.builder().id(pagamentoEntity.getPedido().getId()).build())
				.build();
		return pagamentoDto;
	}

}
