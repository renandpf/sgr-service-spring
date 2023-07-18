package br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagamentoController {
    private EfetuarPagamentoUseCase efetuarPagamentoUseCase;
    private ConfirmarPagamentoUseCase confirmarPagamentoUseCase;

//    @Post("/pagamentos/efetuar")
//    @Returns(200)
//    public Long efetuar(/*@BodyParams() */PagamentoJson pagamentoJson) {
//        log.trace("Start pagamentoJson={}", pagamentoJson);
//        /*const returnDto = await */this.efetuarPagamentoUseCase.efetuar(EfetuarPagamentoParamDto.builder().pagamento(pagamentoJson.getDto()).build());
//        const pagamentoId = returnDto.pagamentoId;
//        this.logger.trace("End pagamentoId={}", pagamentoId);
//        return `${pagamentoId}`;
//    }

    
}
