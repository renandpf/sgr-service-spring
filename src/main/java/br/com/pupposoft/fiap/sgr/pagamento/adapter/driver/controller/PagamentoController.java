package br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json.ConfirmacaoPagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/pagamentos")
public class PagamentoController {
    private EfetuarPagamentoUseCase efetuarPagamentoUseCase;
    private ConfirmarPagamentoUseCase confirmarPagamentoUseCase;

	@PostMapping("efetuar")
	@ResponseStatus(HttpStatus.CREATED)
    public Long efetuar(@RequestBody(required = true) PagamentoJson pagamentoJson) {
        log.trace("Start pagamentoJson={}", pagamentoJson);
        EfetuarPagamentoReturnDto returnDto = this.efetuarPagamentoUseCase.efetuar(EfetuarPagamentoParamDto.builder().pagamento(pagamentoJson.getDto()).build());
        Long pagamentoId = returnDto.getPagamentoId();
        log.trace("End pagamentoId={}", pagamentoId);
        return pagamentoId;
    }

	@PostMapping("confirmar")
	@ResponseStatus(HttpStatus.CREATED)
    public void confirmar(@RequestBody(required = true)  ConfirmacaoPagamentoJson confirmacaoPagamentoJson) {
        log.trace("Start confirmacaoPagamentoJson={}", confirmacaoPagamentoJson);

        //fixme: Esta chamada deve ser async
        this.confirmarPagamentoUseCase.confirmar(confirmacaoPagamentoJson.getIdentificador(), confirmacaoPagamentoJson.getStatus());
        log.trace("End");
    }
}
