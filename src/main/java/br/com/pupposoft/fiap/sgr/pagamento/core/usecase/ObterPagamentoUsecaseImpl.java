package br.com.pupposoft.fiap.sgr.pagamento.core.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ObterPagamentoUsecaseImpl implements ObterPagamentoUsecase {
	
	private PagamentoGateway pagamentoGateway;
	
	@Override
	public PagamentoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
		log.trace("Start identificadorPagamento={}", identificadorPagamento);
		Optional<PagamentoDto> pagamentoDtoOp = pagamentoGateway.obterPorIdentificadorPagamento(identificadorPagamento);
		if(pagamentoDtoOp.isEmpty()) {
			log.warn("Pagamento não foi encontrado. identificadorPagamento={}", identificadorPagamento);
			throw new PagamentoNaoEncontradoException();
		}
		PagamentoDto pagamentoDto = pagamentoDtoOp.get();
		log.trace("End pagamentoDto={}", pagamentoDto);
		return pagamentoDto;
	}
}
