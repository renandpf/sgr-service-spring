package br.com.pupposoft.fiap.sgr.pagamento.core.controller;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecase;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PagamentoController {
	
    private EfetuarPagamentoUseCase efetuarPagamentoUseCase;
	
    private ConfirmarPagamentoUseCase confirmarPagamentoUseCase;
	
    private ObterPagamentoUsecase obterPagamentoUseCase;

    public EfetuarPagamentoReturnDto efetuar(EfetuarPagamentoParamDto paramsDto) {
        log.trace("Start paramsDto={}", paramsDto);
        EfetuarPagamentoReturnDto returnDto = this.efetuarPagamentoUseCase.efetuar(paramsDto);
        log.trace("End returnDto={}", returnDto);
        return returnDto;
    }

    public void confirmar(String identificador, Status status) {
        log.trace("Start identificador={}, status={}", identificador, status);

        //TODO - alterar este método para NÃO receber o status. Na pratica o sistema deve ir buscar o status no sistema terceiro
        //FIXME: Esta chamada deve ser async
        confirmarPagamentoUseCase.confirmar(identificador, status.name());//FIXME: status -> passar o enum
        log.trace("End");
    }

	public PagamentoDto obterByIdentificadorPagamento(String identificadorPagamentoExterno) {
		log.trace("Start identificadorPagamento={}", identificadorPagamentoExterno);
		PagamentoDto dto = obterPagamentoUseCase.obterPorIdentificadorPagamento(identificadorPagamentoExterno);
		log.trace("End dto={}", dto);
		return dto;
	}
}
