package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObterPagamentoUsecaseImpl implements ObterPagamentoUsecase {
	
	@Autowired
	private PagamentoGateway pagamentoRepositoryGateway;
	
	@Override
	public PagamentoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
		log.trace("Start identificadorPagamento={}", identificadorPagamento);
		Optional<PagamentoDto> pagamentoDtoOp = pagamentoRepositoryGateway.obterPorIdentificadorPagamento(identificadorPagamento);
		if(pagamentoDtoOp.isEmpty()) {
			log.warn("Pagamento não foi encontrado. identificadorPagamento={}", identificadorPagamento);
			throw new PagamentoNaoEncontradoException();
		}
		PagamentoDto pagamentoDto = pagamentoDtoOp.get();
		log.trace("End pagamentoDto={}", pagamentoDto);
		return pagamentoDto;
	}
}
