package br.com.pupposoft.fiap.sgr.pagamento.adapter.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PlataformaPagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.StatusPlataformaPagamento;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.PlataformaPagamentoEntityRepository;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigParamsDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.PlataformaPagamentoConfigReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessRepositoryException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoConfigGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PlataformaPagamentoConfigMySqlRepository implements PlataformaPagamentoConfigGateway {
	
	@Autowired
	private PlataformaPagamentoEntityRepository plataformaPagamentoEntityRepository;
	
	@Override
	public PlataformaPagamentoConfigReturnDto obter(PlataformaPagamentoConfigParamsDto paramsDto) {
		try {
			log.trace("Start paramsDto={}", paramsDto);
			Optional<PlataformaPagamentoEntity> plataformaPagamentoEntityOp = plataformaPagamentoEntityRepository.findByStatus((long) StatusPlataformaPagamento.ATIVO.ordinal()).stream().findAny();
			
			PlataformaPagamento plataformaPagamento = PlataformaPagamento.MOCK;
			
			if(plataformaPagamentoEntityOp.isEmpty()) {
				log.warn("Nenhuma plataforma de pagamento ativa! Verifique as configurações (## UTILIZANDO MOCK ##)");
				//TODO: Deveria lançar exceção 
			} else {
				plataformaPagamento = PlataformaPagamento.valueOf(plataformaPagamentoEntityOp.get().getCode());
			}
			
			PlataformaPagamentoConfigReturnDto returnDto = PlataformaPagamentoConfigReturnDto.builder()
					.plataformaPagamento(plataformaPagamento)
					.build();
			
			log.trace("End returnDto={}", returnDto);
			
			return returnDto;
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}
}
