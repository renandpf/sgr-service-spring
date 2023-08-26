package br.com.pupposoft.fiap.sgr.pagamento.core.controller;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.AtualizarStatusPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PagamentoController {
	
    private EfetuarPagamentoUseCase efetuarPagamentoUseCase;
	
    private AtualizarStatusPagamentoUseCase atualizarStatusPagamentoUseCase;
	
    private ObterPagamentoUsecase obterPagamentoUseCase;

    public EfetuarPagamentoReturnDto efetuar(EfetuarPagamentoParamDto paramsDto) {
        log.trace("Start paramsDto={}", paramsDto);
        EfetuarPagamentoReturnDto returnDto = this.efetuarPagamentoUseCase.efetuar(paramsDto);
        log.trace("End returnDto={}", returnDto);
        return returnDto;
    }

    public void notificacoes(PlataformaPagamento plataformaPagamento, String identificadorPagamento) {
        log.trace("Start plataformaPagamento={}, identificadorPagamento={}", plataformaPagamento, identificadorPagamento);

        atualizarStatusPagamentoUseCase.atualizar(plataformaPagamento, identificadorPagamento);
        
        log.trace("End");
    }

	public PagamentoDto obterByIdentificadorPagamento(String identificadorPagamentoExterno) {
		log.trace("Start identificadorPagamento={}", identificadorPagamentoExterno);
		PagamentoDto dto = obterPagamentoUseCase.obterPorIdentificadorPagamento(identificadorPagamentoExterno);
		log.trace("End dto={}", dto);
		return dto;
	}
}
