package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.PedidoEntityRepository;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PedidoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessRepositoryException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PedidoMySqlRepositoryGateway implements PedidoRepositoryGateway {

	private PedidoEntityRepository pedidoEntityRepository;
	
	@Override
	public Long criar(PedidoDto pedido) {
        try {
            log.trace("Start pedido={}", pedido);

            PedidoEntity pedidoEntity = new PedidoEntity(pedido);
            pedidoEntityRepository.save(pedidoEntity);
            Long pedidoCreatedId = pedidoEntity.getId();

//            if (pedidoEntity.getI) {
//                for (let i = 0; i < pedidoEntity.itens.length; i++) {
//                    const item = pedidoEntity.itens[i];
//                    item.pedido = pedidoEntityCreated;
//                    await this.pedidoItemRepository.save(item);
//                }
//            }

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
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PedidoDto> obterEmAndamento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PedidoDto> obterPorStatusAndIdentificadorPagamento(Status status, String identificadorPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PedidoDto> obterPorIdentificadorPagamento(String identificadorPagamento) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
