package br.com.pupposoft.fiap.sgr.pagamento.adapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.controller.json.ConfirmacaoPagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.controller.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.CartaoCreditoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/pagamentos")
public class PagamentoController {
	
	@Autowired
    private EfetuarPagamentoUseCase efetuarPagamentoUseCase;
	
	@Autowired
    private ConfirmarPagamentoUseCase confirmarPagamentoUseCase;
	
	@Autowired
    private ObterPagamentoUsecase obterPagamentoUseCase;

	@PostMapping("efetuar")
	@ResponseStatus(HttpStatus.CREATED)
    public Long efetuar(@RequestBody(required = true) PagamentoJson pagamentoJson) {
        log.trace("Start pagamentoJson={}", pagamentoJson);
        
        EfetuarPagamentoParamDto paramsDto = mapJsonToDto(pagamentoJson);
        EfetuarPagamentoReturnDto returnDto = this.efetuarPagamentoUseCase.efetuar(paramsDto);
        
        Long pagamentoId = returnDto.getPagamentoId();
        log.trace("End pagamentoId={}", pagamentoId);
        return pagamentoId;
    }

	@PostMapping("confirmar")
	@ResponseStatus(HttpStatus.CREATED)
    public void confirmar(@RequestBody(required = true)  ConfirmacaoPagamentoJson confirmacaoPagamentoJson) {
        log.trace("Start confirmacaoPagamentoJson={}", confirmacaoPagamentoJson);

        //TODO - alterar este método para NÃO receber o status. Na pratica o sistema deve ir buscar o status no sistema terceiro
        //FIXME: Esta chamada deve ser async
        confirmarPagamentoUseCase.confirmar(confirmacaoPagamentoJson.getIdentificador(), confirmacaoPagamentoJson.getStatus());
        log.trace("End");
    }

	@GetMapping("identificador-pagamento-externo/{identificadorPagamentoExterno}")
	public PagamentoJson obterByIdentificadorPagamento(@PathVariable String identificadorPagamentoExterno) {
		log.trace("Start identificadorPagamento={}", identificadorPagamentoExterno);
		PagamentoDto dto = obterPagamentoUseCase.obterPorIdentificadorPagamento(identificadorPagamentoExterno);
		PagamentoJson json = mapDtoToJson(dto);
		log.trace("End json={}", json);
		return json;
	}

	private PagamentoJson mapDtoToJson(PagamentoDto dto) {
		PagamentoJson json = PagamentoJson.builder()
				.id(dto.getId())
				.identificadorPagamento(dto.getIdentificadorPagamentoExterno())
				.pedidoId(dto.getPedido().getId())
				.build();
		return json;
	}
	
	private EfetuarPagamentoParamDto mapJsonToDto(PagamentoJson pagamentoJson) {
		List<CartaoCreditoDto> ccDtoList = pagamentoJson.getCartoesCreditos().stream().map(ccJson -> {
        	return CartaoCreditoDto.builder()
        			.cpf(ccJson.getCpf())
        			.nome(ccJson.getNome())
        			.numero(ccJson.getNumero())
        			.cvv(ccJson.getCvv())
        			.valor(ccJson.getValor())
        			.build();
        }).toList();
        
        EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder()
        .pagamento(PagamentoDto
        		.builder()
        		.id(pagamentoJson.getId())
        		.pedido(PedidoDto.builder().id(pagamentoJson.getPedidoId()).build())
        		.identificadorPagamentoExterno(pagamentoJson.getIdentificadorPagamento())
        		.cartoesCredito(ccDtoList)
        		.build())
        .build();
		return paramsDto;
	}

}
