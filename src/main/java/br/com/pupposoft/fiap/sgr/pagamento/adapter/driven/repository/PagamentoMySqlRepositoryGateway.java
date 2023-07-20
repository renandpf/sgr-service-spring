package br.com.pupposoft.fiap.sgr.pagamento.adapter.driven.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.repository.PagamentoRepository;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PagamentoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessRepositoryException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PagamentoMySqlRepositoryGateway implements PagamentoRepositoryGateway {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Override
	public Long criar(PagamentoDto pagamentoDto) {
        try {
        	log.trace("Start pagamento={}", pagamentoDto);

        	PagamentoEntity pagamentoEntity = PagamentoEntity.builder()
        			.codigoPagamento(pagamentoDto.getIdentificadorPagamentoExterno())
        			//.pedido //FIXME
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

}
